package shapetd;

import shapetd.GameEngine.GameEngine;
import shapetd.GameObjects.GameObject;
import shapetd.GameObjects.Towers.*;
import shapetd.GameObjects.Enemies.*;
import shapetd.GameObjects.HiliteTowerOutlines.HiliteArrowTowerOutline;
import shapetd.GameObjects.HiliteTowerOutlines.HiliteCannonTowerOutline;
import shapetd.GameObjects.HiliteTowerOutlines.HiliteTowerOutline;
import shapetd.GameObjects.Attacks.*;
import shapetd.Panels.GamePlayMenu;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("serial")
public class Game extends JPanel {
	
	private static Game instance;
	
	static final String TITLE = "ShapeTD";
	public static final long UPDATE_PERIOD_NSEC = (long) ((1.0 / 50.0) * Math.pow(10,
			9));
	
	public static final int UPDATE_PERIOD_MSEC = (int)(UPDATE_PERIOD_NSEC / 1000000);
	public static final double UPDATE_PERIOD_SEC = (double) UPDATE_PERIOD_NSEC * Math.pow(10, -9);
	
	public static final int GOLD_START = 250;
	public static final int LIVES_START = 20;
	
	public static final boolean TEST_MODE = false;
	
	public static final int MAX_LEVELS_ALLOWED = 51;
	
	public static final int NUMBER_OF_LEVELS = MAX_LEVELS_ALLOWED;
	
	public static final double BUILD_TOWER_INTEREST = 0.025;
	


	static enum GameState {
		INIT, PLAY, PAUSE, GAMEOVER, RESTART, EXIT
	}

	static volatile GameState state;

	static private GameCanvas canvas;
	static private GamePlayMenu tmenu;

	static final Dimension SCREEN_DIMENSIONS = Toolkit.getDefaultToolkit()
			.getScreenSize();
	public static final int CANVAS_HEIGHT = 800; //SCREEN_DIMENSIONS.height;
	public static final int CANVAS_WIDTH = 800;//CANVAS_HEIGHT;
	
	public static final double BASE_INTEREST = 0.01;

	// Game objects
	static private GameEngine engine;
	static private List<Tower> towers;
	static private List<Enemy> enemies;
	static private HiliteTowerOutline hilite_outline;
	static private GameGrid grid;
	static private volatile boolean build_new_tower;
	static private volatile boolean update_paths;
	
	private static Tower selectedTower;
	private static Enemy selectedEnemy;

	private int gold;
	private int lives;
	private Level[] levels;
	private int numberOfLevels;
	private int currentLevel;
	private double interestRate;
	
	private Timer loopTimer;
	
	// initialize game objects, run once
	private void gameInit() {
		state = GameState.INIT;
		engine = new GameEngine();
		towers = new CopyOnWriteArrayList<Tower>();
		enemies = new CopyOnWriteArrayList<Enemy>();
		hilite_outline = null; 
		grid = new GameGrid();
		build_new_tower = false;
		update_paths = false;
		
		ArrowTower.CREATION_COST = ArrowTower.BASE_CREATION_COST;
		CannonTower.CREATION_COST = CannonTower.BASE_CREATION_COST;
		
		instance = this;
		gold = GOLD_START;
		lives = LIVES_START;
		selectedTower = null;
		selectedEnemy = null;
		interestRate = BASE_INTEREST;
		
		levels = LevelGenerator.generateLevels(grid);
		numberOfLevels = levels.length;
		currentLevel = -1;
		
		tmenu.updateGoldLabel(gold);
		tmenu.updateLivesLabel(lives);
		tmenu.updateEnemiesLabel(0);
		tmenu.updateLevelLabel(0);
		tmenu.updateArrowTowerCost();
		tmenu.updateCannonTowerCost();
		tmenu.setAllEnabled();
		tmenu.hideInfoPanel();
	}
	
	public static Game getInstance() {
		return instance;
	}
	
	public GameGrid getGrid() {
		return grid;
	}
	
	private static void disposePreviousGame() {
		canvas.removeAll();
		instance.remove(tmenu);
	}

	private Game() {
		canvas = new GameCanvas();
		canvas.setLayout(null);
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		// canvas.setPreferredSize(SCREEN_DIMENSIONS);
		add(canvas);
		
		tmenu = new GamePlayMenu(SCREEN_DIMENSIONS, gold, lives);
		tmenu.setBounds(100, 100, 80, 80);
		add(tmenu);
		
		this.setDoubleBuffered(true);
		// initialize game objects
		gameInit();


		// ADD MORE UI COMPONENTS SUCH AS BUTTONS OR MENU
		gameStart();
	}

	// Refresh display after each step
	// Use graphics g as argument if not using java 2D
	public static void gameDraw(Graphics2D g2d) {
		switch (state) {
		case INIT:
			break;

		case PLAY:
			drawGameObjects(g2d);
			break;

		case GAMEOVER:
			break;

		case PAUSE:
			break;

		case EXIT:
			break;
		}
	}

	public static void drawGameObjects(Graphics2D g2d) {
		
		for (Enemy enemy : enemies) {
			enemy.draw(g2d);
		}
		
		for (Tower tower : towers) {
			tower.draw(g2d);
			
			for(Attack attack : tower.getAttacks()) {
				attack.draw(g2d);
				Point2D impact = attack.getTargetImpactPoint();
				g2d.draw(new Ellipse2D.Double(impact.getX(), impact.getY(), 3, 3));
			}
		}	
		
		if(build_new_tower && hilite_outline != null) {
			hilite_outline.draw(g2d);
		}
		
		//g2d.setColor(Color.BLACK);
		//Shape star = StarEnemy.createStar(100.0, 400.0);
		//g2d.fill(star);
	}

	public void gameStart() {
		
		state = GameState.PLAY;
		if(loopTimer == null) {
			loopTimer = new Timer(UPDATE_PERIOD_MSEC, gameLoop);
			loopTimer.start();
		}
	}

	Action gameLoop = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			// MAIN GAME LOOP
			
			long begintime, timetaken, timeleft;
			if (state != GameState.GAMEOVER) {
				begintime = System.nanoTime();
				if (state == GameState.PLAY) {
					gameUpdate();
					repaint();
				}else if(state == GameState.EXIT) {
					repaint();
				}else if(state == GameState.RESTART) {
					repaint();
				}
				
				// refresh display
				//timetaken = System.nanoTime() - begintime;
				//timeleft = Math
				//		.max((UPDATE_PERIOD_NSEC - timetaken) / 1000000L, 10); // in
																				// milliseconds
			}else if(state == GameState.GAMEOVER) {
				if(JOptionPane.showConfirmDialog(
					    null,
					    "GAME OVER\n"+
					    "Would You Like To Play Again?",
					    "Restart Game",
					    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					
					gameInit();
					gameStart();
				}
				else {
					System.exit(0);
				}
			}
			
			if(state == GameState.RESTART) {
				
				gameInit();
				gameStart();
			}
		}
	};
	
	
	public void gameLoop() {

		//towers.add(new ArrowTower(390, 390));
		//towers.add(new ArrowTower(458.5714, 390));
		//Point2D.Double point = grid.findDestination(30, -20);
		//Point p = grid.findDestinationIndex(point);
		//enemies.add(new CircleEnemy(30, -10, point, p));
		//repaint();
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// MAIN GAME LOOP
		
		
		long begintime, timetaken, timeleft;
		while (state != GameState.GAMEOVER) {
			begintime = System.nanoTime();
			if (state == GameState.PLAY) {
				gameUpdate();
				repaint();
			}else if(state == GameState.EXIT) {
				repaint();
				break;
			}else if(state == GameState.RESTART) {
				repaint();
				break;
			}
			
			// refresh display
			timetaken = System.nanoTime() - begintime;
			timeleft = Math
					.max((UPDATE_PERIOD_NSEC - timetaken) / 1000000L, 10); // in
																			// milliseconds
			try {
				Thread.sleep(timeleft);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
		if(state == GameState.GAMEOVER) {
			if(JOptionPane.showConfirmDialog(
				    null,
				    "GAME OVER\n"+
				    "Would You Like To Play Again?",
				    "Restart Game",
				    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
				state = GameState.RESTART;
			}
			else {
				System.exit(0);
			}
		}
		
		if(state == GameState.RESTART) {
			
			gameInit();
			gameStart();
		}else {
			System.exit(0);
		}
	}

	// detect collisions and provide responses
	// update all game objects
	public void gameUpdate() {
		//engine.updateAttacks(attacks);
		
		if(!update_paths) {
			engine.updateTowers(towers, enemies);
			engine.updateEnemies(enemies, grid);
		}

	}
	
	public void pathsUpdated() {
		update_paths = false;
	}
	
	public void upgradeSelectedTower() {
		int cost = selectedTower.getUpgradeCost();
		if(!selectedTower.levelUp()) return;
		
		removeGold(cost);
		tmenu.updateTowerPanel(selectedTower, gold);
	}
	
	public void sellSelectedTower() {
		removeTower(selectedTower);
	}

	public static void gameMouseClicked(MouseEvent e) {
		int x, y;
		x = e.getX();
		y = e.getY();
		
		//System.out.println("click");
		
		if(selectedTower != null) {
			selectedTower.setRangeVisible(false);
			selectedTower = null;
			tmenu.hideInfoPanel();
		}
		
		if(selectedEnemy != null) {
			selectedEnemy.select(false);
			selectedEnemy = null;
			tmenu.hideInfoPanel();
		}
		
		if(build_new_tower && hilite_outline != null) {
			
			build_new_tower = false;
			update_paths = true;
			boolean success = engine.placeTower(hilite_outline, grid);
			update_paths = false;
			
			for(MouseMotionListener m : canvas.getMouseMotionListeners()) {
				canvas.removeMouseMotionListener(m);
			}
			
			hilite_outline = null;
		}
		
		GameObject clicked = engine.mouseClicked(x, y, towers, enemies);

		if(clicked != null) {
			if (clicked instanceof Tower) {
				Tower tower = (Tower) clicked;
				tower.setRangeVisible(true);
				selectedTower = tower;
				tmenu.updateTowerPanel(tower, instance.gold);
			}else {
				//System.out.println("Enemy clicked");
				Enemy enemy = (Enemy) clicked;
				enemy.select(true);
				selectedEnemy = enemy;
				tmenu.updateEnemyPanel(enemy);
			}
		}
	}
	
	public void updateEnemyInfo(Enemy e) {
		if(selectedEnemy == null) return;
		if(selectedEnemy.equals(e)) {
			tmenu.updateEnemyPanel(e);
		}
	}
	
	public void updateInterest() {
		if(currentLevel > 25) {
			interestRate = 0.15 + ((currentLevel - 26) / 5) * 0.025;
		}else {
			//interestRate = (( (int)(currentLevel - 1) / 5) + 1) * 0.02;
			interestRate = 0.01;
		}
		
		//System.out.println("Interest on gold is now: "+interestRate);
	}
	
	public void startLevel() {
		currentLevel++;
		updateInterest();
		enemies = levels[currentLevel].getEnemies();
		//System.out.println("New Enemy. Health: "+enemies.get(0).getHealth()+" Speed: "+enemies.get(0).getSpeed());
		tmenu.updateLevelLabel(currentLevel);
		tmenu.updateEnemiesLabel(enemies.size());
	}
	
	public void changeLevel(int level) {
		for(Enemy e : new ArrayList<Enemy>(enemies)) {
			e.killEnemy();
			removeEnemy(e);
		}
		
		currentLevel = level;
		enemies = levels[currentLevel].getEnemies();
		tmenu.updateLevelLabel(currentLevel);
		tmenu.updateEnemiesLabel(enemies.size());
	}
	
	public static void pause() {
		if(state == GameState.PAUSE) {
			state = GameState.PLAY;
		}else if(state == GameState.PLAY) {
			state = GameState.PAUSE;
		}
	}
	
	public static void restart() {
		state = GameState.RESTART;
	}
	
	public void addTower(Tower t) {
		if(gold >= t.getCreationCost()) {
			removeGold(t.getCreationCost());
			towers.add(t);
			t.increaseCreationCost();
			tmenu.updateArrowTowerCost();
			tmenu.updateCannonTowerCost();
		}
	}
	
	public void removeGold(int spent) {
		gold -= spent;
		disableTowerBuilding();
		tmenu.updateGoldLabel(gold);
	}
	
	public void addGold(int gained) {
		gold += gained;
		enableTowerBuilding();
		tmenu.updateGoldLabel(gold);
		
		if(selectedTower != null) {
			tmenu.updateTowerPanel(selectedTower, gold);
		}
	}
	
	public void enableTowerBuilding() {
		if(gold >= ArrowTower.CREATION_COST) tmenu.enableBuildArrowTower(true);
		if(gold >= CannonTower.CREATION_COST) tmenu.enableBuildCannonTower(true);
	}
	
	public void disableTowerBuilding() {
		if(gold < ArrowTower.CREATION_COST) tmenu.enableBuildArrowTower(false);
		if(gold < CannonTower.CREATION_COST) tmenu.enableBuildCannonTower(false);
	}
	
	public void removeTower(Tower t) {
		int money = selectedTower.getDestroyValue();
		if(!towers.remove(selectedTower)) return;
		
		addGold(money);
		tmenu.hideInfoPanel();
		t.decreaseCreationCost();
		tmenu.updateArrowTowerCost();
		tmenu.updateCannonTowerCost();
		
		update_paths = true;
		grid.unblockTowerSpace(t);
		grid.updatePaths(towers);
		update_paths = false;
	}
	
	public void addEnemy(Enemy e) {
		enemies.add(e);
		tmenu.updateEnemiesLabel(enemies.size());
	}
	
	public void removeEnemy(Enemy e) {
		if(e.isKilled()) {
			if(enemies.remove(e)) {
				addGold(e.getReward());
			}
		}else {
			e.killEnemy();
			if(enemies.remove(e)) {
				removeLife();
			}
		}
		
		if(selectedEnemy != null && selectedEnemy.equals(e)) {
			tmenu.hideInfoPanel();
			selectedEnemy = null;
		}
		
		tmenu.updateEnemiesLabel(enemies.size());
		
		if(enemies.isEmpty()) {
			if((currentLevel + 1) == numberOfLevels) {
				if(JOptionPane.showConfirmDialog(
					    null,
					    "YOU WIN!\n"+
					    "Would You Like To Play Again?",
					    "Winner",
					    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					
					state = GameState.RESTART;
				}
				else {
					state = GameState.EXIT;
				}
			}
			
			compoundGold();
			tmenu.enableLevelStart(true);
		}
	}
	
	public void compoundGold() {
		int interest = (int) (gold * interestRate);
		addGold(interest);
	}
	
	public void removeLife() {
		lives--;
		if(lives == 0) {
			state = GameState.GAMEOVER;
		}
		tmenu.updateLivesLabel(lives);
	}
	
	public static void gameMouseMoved(MouseEvent e) {
		double x, y;
		x = (double) e.getX();
		y = (double) e.getY();
		hilite_outline = grid.hiliteTowerSquare(x,y, hilite_outline);
	}
	
	public static void buildArrowTower(ActionEvent e) {
		build_new_tower = true;
		hilite_outline = new HiliteArrowTowerOutline();
		canvas.addMouseMotionListener(canvas);
	}
	
	public static void buildCannonTower(ActionEvent e) {
		build_new_tower = true;
		hilite_outline = new HiliteCannonTowerOutline();
		canvas.addMouseMotionListener(canvas);
	}
	
	public static void spawnEnemy() {
		int width = ThreadLocalRandom.current().nextInt(20, CANVAS_WIDTH - 20 + 1);
		Point2D.Double point = grid.findDestination(width, -10);
		Point p = grid.findDestinationIndex(point);
		enemies.add(new CircleEnemy(width, -50, point, p, 1));
	}

	public void gameShutdown() {

	}
	
	public List<Tower> getTowers() {
		return towers;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}

	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame(TITLE);
				// frame.setLayout(new BorderLayout());
				frame.setExtendedState(Frame.MAXIMIZED_BOTH);
				// System.out.println(frame.getSize());
				// Set the content-pane of the JFrame to an instance of main
				// JPanel
				frame.setContentPane(new Game()); // main JPanel as content pane
				// frame.setJMenuBar(menuBar); // menu-bar (if defined)
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// frame.pack();
				frame.setLocationRelativeTo(null); // center the application
													// window
				frame.setVisible(true); // show it
			}
		});
	}

}

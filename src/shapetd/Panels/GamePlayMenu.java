package shapetd.Panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JComboBox;

import shapetd.Game;
import shapetd.GameObjects.Enemies.Enemy;
import shapetd.GameObjects.Towers.ArrowTower;
import shapetd.GameObjects.Towers.CannonTower;
import shapetd.GameObjects.Towers.Tower;

import java.awt.CardLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("serial")
public class GamePlayMenu extends JPanel{
	
	JButton buildArrowTower;
	JButton spawnEnemy;
	JLabel goldLabel;
	JLabel livesLabel;
	JButton pause;
	JButton restart;
	JButton start;
	JComboBox<Integer> levelList;
	JLabel enemies;
	JLabel currentLevel;
	JLabel arrowTowerCost;
	
	
	//Tower panel
	TowerInfoPanel towerPanel;
	
	
	//enemy panel
	EnemyInfoPanel enemyPanel;
	
	
	JPanel blankPanel;
	
	JPanel infoPanel;
	CardLayout layout;
	private JLabel cannonTowerCost;
	private JButton buildCannonTower;
	
	public GamePlayMenu(Dimension dimensions, int gold, int lives) {
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{125, 125, 125, 125, 0};
		gridBagLayout.rowHeights = new int[]{23, 0, 0, 0, 0, 51, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel goldLabel;
		goldLabel = new JLabel("Gold: "+gold);
		GridBagConstraints gbc_lblGold = new GridBagConstraints();
		gbc_lblGold.insets = new Insets(0, 0, 5, 5);
		gbc_lblGold.gridx = 0;
		gbc_lblGold.gridy = 0;
		add(goldLabel, gbc_lblGold);
		this.goldLabel = goldLabel;
		goldLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		
		JButton enemyButton;
		
		
		JLabel lblLives = new JLabel("Lives: "+lives);
		GridBagConstraints gbc_lblLives = new GridBagConstraints();
		gbc_lblLives.insets = new Insets(0, 0, 5, 5);
		gbc_lblLives.gridx = 1;
		gbc_lblLives.gridy = 0;
		add(lblLives, gbc_lblLives);
		livesLabel = lblLives;
		livesLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		
		JLabel lblEnemies = new JLabel("Enemies: "+0);
		GridBagConstraints gbc_lblEnemies = new GridBagConstraints();
		gbc_lblEnemies.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnemies.gridx = 2;
		gbc_lblEnemies.gridy = 0;
		add(lblEnemies, gbc_lblEnemies);
		enemies = lblEnemies;
		enemies.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		
		JLabel lblCurrentLevel = new JLabel("Current Level: "+0);
		GridBagConstraints gbc_lblCurrentLevel = new GridBagConstraints();
		gbc_lblCurrentLevel.insets = new Insets(0, 0, 5, 0);
		gbc_lblCurrentLevel.gridx = 3;
		gbc_lblCurrentLevel.gridy = 0;
		add(lblCurrentLevel, gbc_lblCurrentLevel);
		currentLevel = lblCurrentLevel;
		currentLevel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_3.gridx = 0;
		gbc_verticalStrut_3.gridy = 1;
		add(verticalStrut_3, gbc_verticalStrut_3);
		
		JLabel lblBuildCost = new JLabel("Build Cost: "+ArrowTower.CREATION_COST);
		GridBagConstraints gbc_lblBuildCost = new GridBagConstraints();
		gbc_lblBuildCost.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuildCost.gridx = 0;
		gbc_lblBuildCost.gridy = 2;
		add(lblBuildCost, gbc_lblBuildCost);
		arrowTowerCost = lblBuildCost;
		
		JLabel lblBuildCost_1 = new JLabel("Build Cost: "+CannonTower.CREATION_COST);
		GridBagConstraints gbc_lblBuildCost_1 = new GridBagConstraints();
		gbc_lblBuildCost_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuildCost_1.gridx = 1;
		gbc_lblBuildCost_1.gridy = 2;
		add(lblBuildCost_1, gbc_lblBuildCost_1);
		cannonTowerCost = lblBuildCost_1;
		
		JButton arrowTower = new JButton("Arrow Tower");
		//arrowTower.setBounds(x, y, 60, 60);
		arrowTower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.buildArrowTower(e);
			}
		});
		buildArrowTower = arrowTower;
		
		GridBagConstraints gbc_arrowTower = new GridBagConstraints();
		gbc_arrowTower.insets = new Insets(0, 0, 5, 5);
		gbc_arrowTower.gridx = 0;
		gbc_arrowTower.gridy = 3;
		add(arrowTower, gbc_arrowTower);	
		
		
		JButton btnCannonTower = new JButton("Cannon Tower");
		btnCannonTower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.buildCannonTower(e);
			}
		});
		GridBagConstraints gbc_btnCannonTower = new GridBagConstraints();
		gbc_btnCannonTower.insets = new Insets(0, 0, 5, 5);
		gbc_btnCannonTower.gridx = 1;
		gbc_btnCannonTower.gridy = 3;
		add(btnCannonTower, gbc_btnCannonTower);
		buildCannonTower = btnCannonTower;
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 4;
		add(verticalStrut_1, gbc_verticalStrut_1);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 2;
		gbc_verticalStrut_2.gridy = 4;
		add(verticalStrut_2, gbc_verticalStrut_2);
		enemyButton = new JButton("Spawn Enemy");
		enemyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.spawnEnemy();
			}
		});
		
		GridBagConstraints gbc_enemyButton = new GridBagConstraints();
		gbc_enemyButton.insets = new Insets(0, 0, 5, 5);
		gbc_enemyButton.gridx = 0;
		gbc_enemyButton.gridy = 5;
		add(enemyButton, gbc_enemyButton);
		spawnEnemy = enemyButton;
		spawnEnemy.setVisible(Game.TEST_MODE);
		
		JButton btnChangeLevel = new JButton("Change Level");
		btnChangeLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.getInstance().changeLevel((int)levelList.getSelectedItem());
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 5;
		add(btnChangeLevel, gbc_btnNewButton);
		btnChangeLevel.setVisible(Game.TEST_MODE);
		
		
		JComboBox comboBox = new JComboBox();
		for(int i = 0; i<Game.NUMBER_OF_LEVELS; i++) comboBox.addItem(i);
		comboBox.setSelectedIndex(0);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 5;
		add(comboBox, gbc_comboBox);
		levelList = comboBox;
		levelList.setVisible(Game.TEST_MODE);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 6;
		add(verticalStrut, gbc_verticalStrut);
		
		JButton btnPause = new JButton("Pause");
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.insets = new Insets(0, 0, 5, 5);
		gbc_btnPause.gridx = 0;
		gbc_btnPause.gridy = 7;
		add(btnPause, gbc_btnPause);
		pause = btnPause;
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.pause();
			}
		});
		
		JButton btnRestart = new JButton("Restart");
		GridBagConstraints gbc_btnRestart = new GridBagConstraints();
		gbc_btnRestart.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestart.gridx = 1;
		gbc_btnRestart.gridy = 7;
		add(btnRestart, gbc_btnRestart);
		restart = btnRestart;
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.restart();
			}
		});
		
		JButton btnStartLevel = new JButton("Start Level");
		GridBagConstraints gbc_btnStartGame = new GridBagConstraints();
		gbc_btnStartGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnStartGame.gridx = 2;
		gbc_btnStartGame.gridy = 7;
		add(btnStartLevel, gbc_btnStartGame);
		start = btnStartLevel;
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.getInstance().startLevel();
				start.setEnabled(false);
			}
		});
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_4.gridx = 0;
		gbc_verticalStrut_4.gridy = 8;
		add(verticalStrut_4, gbc_verticalStrut_4);
		
		
		/************ Tower info panel *********************/
		
		JPanel tPanel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 2;
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 9;
		add(tPanel, gbc_panel_1);
		
		tPanel.setLayout(new CardLayout(0, 0));
		
		TowerInfoPanel panel = new TowerInfoPanel();
		tPanel.add(panel, "Tower Panel");
		towerPanel = panel;
				
		
		
		/*********** enemy panel ****************/
		EnemyInfoPanel ePanel = new EnemyInfoPanel();
		tPanel.add(ePanel, "Enemy Panel");
		enemyPanel = ePanel;
		

		
		
		JPanel blank = new JPanel();
		blankPanel = blank;
		tPanel.add(blank, "Blank Panel");
		
		infoPanel = tPanel;
		layout = (CardLayout) tPanel.getLayout();
		layout.show(tPanel, "Blank Panel");
	}

	
	public void setAllEnabled() {
		buildArrowTower.setEnabled(true);
		buildCannonTower.setEnabled(true);
		spawnEnemy.setEnabled(true);
		start.setEnabled(true);
	}
	
	public void hideInfoPanel() {
		layout.show(infoPanel, "Blank Panel");
	}
	
	public void updateEnemyPanel(Enemy e) {
		enemyPanel.updateEnemyPanel(e);
		layout.show(infoPanel, "Enemy Panel");
	}
	
	public void updateTowerPanel(Tower tower, int gold) {
		towerPanel.updateTowerPanel(tower, gold);
		layout.show(infoPanel, "Tower Panel");
	}
							
	
	public void updateEnemiesLabel(int enemies) {
		this.enemies.setText("Enemies: "+enemies);
		revalidate();
	}
	
	public void updateLevelLabel(int level) {
		this.currentLevel.setText("Current Level: "+level);
		revalidate();
	}
	
	public void updateGoldLabel(int gold) {
		this.goldLabel.setText("Gold: "+gold);
		revalidate();
	}
	
	public void updateLivesLabel(int lives) {
		this.livesLabel.setText("Lives: "+lives);
		revalidate();
	}
	
	public void updateArrowTowerCost() {
		arrowTowerCost.setText("Build Cost: "+Math.min(ArrowTower.CREATION_COST, ArrowTower.MAX_CREATION_COST));
	}
	
	public void updateCannonTowerCost() {
		cannonTowerCost.setText("Build Cost: "+Math.min(CannonTower.CREATION_COST, CannonTower.MAX_CREATION_COST));
	}
	
	public void enableBuildArrowTower(boolean enable) {
		buildArrowTower.setEnabled(enable);
	}
	
	public void enableBuildCannonTower(boolean enable) {
		buildCannonTower.setEnabled(enable);
	}
	
	public void enableLevelStart(boolean enable) {
		start.setEnabled(enable);
	}
	
}

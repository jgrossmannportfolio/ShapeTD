package shapetd.GameObjects.Towers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shapetd.GameGrid;
import shapetd.GameObjects.Colors;
import shapetd.GameObjects.GameObject;
import shapetd.GameObjects.Attacks.Attack;
import shapetd.GameObjects.Enemies.Enemy;

@SuppressWarnings("serial")
public abstract class Tower extends GameObject {
	
	static final int MAX_LEVEL = 4;
	
	protected List<Attack> attacks;
	protected int level;
	protected int value;
	protected int creation_cost;
	
	protected int attack_damage;
	protected double attack_speed;
	protected int attack_attributes;
	protected double attack_timer;
	protected int range;
	
	protected Ellipse2D.Double rangeCircle;
	protected Point2D.Double[] grid_squares;
	protected Point[] grid_square_indexes;
	boolean attackReady;
	Enemy target;
	boolean rangeVisible;
	
	int splashRange;
	int splashDamage;
		
	// Standard grid square edge is 6.667% of max screen height
	public static final double TOWER_WIDTH = (GameGrid.grid_square_width * 2) + GameGrid.grid_square_spacing;

	public Tower(RectangularShape graphic, int level, int ccost, Point[] indexes, int range, double speed, 
								int damage, int splashRange, int splashDamage, Color color) {
		super(graphic, color);
		// this.graphic = graphic;
		this.level = level;
		this.value = ccost;
		this.creation_cost = ccost;
		//this.grid_squares = squares;
		this.grid_square_indexes = indexes;
				
		setAttackValues(damage, speed, range, splashRange, splashDamage);
		
		// this.setLocation(x, y);
		attack_timer = 0;
		attacks = new CopyOnWriteArrayList<Attack>();
		attackReady = true;
		target = null;
		rangeVisible = false;
	}

	public List<Attack> getAttacks() {
		return this.attacks;
	}
	
	public abstract void addAtack();

	public void removeAttack(Attack attack) {
		attacks.remove(attack);
	}
	
	public Shape getRangeCircle() {
		return rangeCircle;
	}
	
	public boolean hasTarget() {
		if(target != null) {
			if(target.isKilled()) return false;
			if(rangeCircle.intersects(target.getGraphic().getBounds2D())) {
				return true;
			}
		}
		return false;
	}
	
	public void setTarget(Enemy target) {
		this.target = target;
	}
	
	public int getLevel() {
		return this.level;
	}

	public boolean attackReady() {
		return attackReady;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getSplashRange() {
		return splashRange;
	}
	
	public int getSplashDamage() {
		return splashDamage;
	}

	public int getCreationCost() {
		return this.creation_cost;
	}

	public void setCreationCost(int ccost) {
		this.creation_cost = ccost;
	}

	public void addToCreationCost(int cost) {
		this.creation_cost += cost;
	}

	public abstract int getUpgradeCost();

	public int getDestroyValue() {
		return (int) (this.value * 0.75);
	}

	public Point2D.Double[] getGridSquares() {
		return this.grid_squares;
	}

	public void setGridSquares(Point2D.Double[] squares) {
		this.grid_squares = squares;
	}

	public Point[] getGridSquareIndexes() {
		return this.grid_square_indexes;
	}
	
	public void removeTarget() {
		target = null;
	}

	public void setGridSquareIndexes(Point[] indexes) {
		this.grid_square_indexes = indexes;
	}
	
	public int getRange() {
		return this.range;
	}
	
	public void setRange(int range) {
		this.range = range;
		double diameter = range * 2.0;
		this.rangeCircle = new Ellipse2D.Double(getCurrentLocation().x-range, getCurrentLocation().y-range, diameter, diameter);
	}
	
	public void setRangeVisible(boolean visible) {
		rangeVisible = visible;
	}
		
	
	public boolean updateAttackTimer(double dt) {
		if(attackReady) return true;
		
		attack_timer += dt;

		double thresh = 1.0 / attack_speed;
		if(attack_timer >= thresh) {
			attack_timer -= thresh;
			attackReady = true;
			return true;
		}
		return false;
	}
	
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.color);
		g2d.fill(this.graphic);
		
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Colors.getTowerLevelColor(level));
		g2d.draw(graphic);
		
		g2d.setStroke(new BasicStroke());
		
		if(rangeVisible) {
			g2d.setColor(Colors.tower_range);
			g2d.fill(this.rangeCircle);
		}
	}
	
	public abstract void increaseCreationCost();
	
	public abstract void decreaseCreationCost();

	public abstract boolean levelUp();
	
	public abstract String getType();

	public void setAttackValues(int attackDamage, double attackSpeed, int range,
									int splashRange, int splashDamage) {
		attack_damage = attackDamage;
		
		BigDecimal bd = new BigDecimal(attackSpeed);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    attack_speed = bd.doubleValue();
	    
		setRange(range);
		
		this.splashRange = splashRange;
		this.splashDamage = splashDamage;
		attack_timer = 0;
	}
	
	public int getAttackDamage() {
		return attack_damage;
	}
	
	public double getAttackSpeed() {
		return attack_speed;
	}
	
	public boolean fullyUpgraded() {
		return level == MAX_LEVEL;
	}
	
	public abstract int getNextSplashRange();
	
	public abstract int getNextSplashDamage();
	
	public abstract int getNextRange();
	
	public abstract int getNextDamage();
	
	public abstract double getNextSpeed();
	
	public int getNextLevel() {
		if(level < MAX_LEVEL) {
			return level + 1;
		}
		return MAX_LEVEL;
	}
}

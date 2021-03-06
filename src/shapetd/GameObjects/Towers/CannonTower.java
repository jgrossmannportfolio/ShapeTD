package shapetd.GameObjects.Towers;

import java.awt.Point;
import java.awt.geom.RoundRectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

import shapetd.GameObjects.Colors;
import shapetd.GameObjects.Attacks.ArrowAttack;
import shapetd.GameObjects.Attacks.Attack;
import shapetd.GameObjects.Attacks.CannonAttack;
import shapetd.GameObjects.GameObject.Attributes;

public class CannonTower extends Tower{
	public static int BASE_CREATION_COST = 25;
	
	public static int CREATION_COST = BASE_CREATION_COST;
	
	public static int MAX_CREATION_COST = 35;
	
	public final static int BASE_RANGE = 120;
	public final static double BASE_SPEED = 0.5;
	public final static int BASE_DAMAGE = 40;
	
	final static int BASE_SPLASH_RANGE = 50;
	final static double BASE_SPLASH_RATIO = 0.3;

	public CannonTower(double x, double y, Point[] indexes) {
		super(new RoundRectangle2D.Double(x, y, TOWER_WIDTH, TOWER_WIDTH, 8.0,
				8.0), 1, Math.min(CREATION_COST, MAX_CREATION_COST), indexes, BASE_RANGE, BASE_SPEED, BASE_DAMAGE, 
				BASE_SPLASH_RANGE, (int)(BASE_SPLASH_RATIO * BASE_DAMAGE), Colors.blue);
		
	}
	
	public void increaseCreationCost() {
		CREATION_COST += 2;
		//CREATION_COST = (int) Math.ceil(CREATION_COST * (1.0 + Game.BUILD_TOWER_INTEREST));
		//System.out.println("cost: "+CREATION_COST);
	}
	
	public void decreaseCreationCost() {
		CREATION_COST -= 2;
		//CREATION_COST = (int) Math.floor(CREATION_COST / (1.0 + Game.BUILD_TOWER_INTEREST));
		//System.out.println("cost: "+CREATION_COST);
	}
	

	@Override
	public boolean levelUp() {
		if(level == MAX_LEVEL) return false;
		
		int newDamage = getNextDamage();
		double nextSpeed = getNextSpeed();
		int nextRange = getNextRange();
		int nextSplashRange = getNextSplashRange();
		int nextSplashDamage = getNextSplashDamage();
		
		value += getUpgradeCost();
		
		this.level += 1;
		
		setAttackValues(newDamage, nextSpeed, nextRange, nextSplashRange, nextSplashDamage);
		return true;
	}
	
	@Override
	public int getUpgradeCost() {
		double cost = BASE_CREATION_COST;
		for(int i = 0; i < level; i++) {
			if(i == MAX_LEVEL - 2) {
				cost *= 3.4;
			}else {
				cost *= 3.2;
			}
		}
		
		return (int) cost;
	}
	
	@Override
	public String getType() {
		return "Cannon Tower";
	}
	
	@Override
	public int getNextDamage() {
		if(!fullyUpgraded()) {
			if(level == MAX_LEVEL - 1) return (int)(getAttackDamage() * 3.5);
			return (int)(getAttackDamage() * 2.5);
		}else {
			return getAttackDamage();
		}
	}
	
	@Override
	public double getNextSpeed() {
		if(!fullyUpgraded()) {
			if(level == MAX_LEVEL - 1) {
				BigDecimal bd = new BigDecimal(getAttackSpeed() + 0.3);
			    bd = bd.setScale(2, RoundingMode.HALF_UP);
			    return bd.doubleValue();
			}else {
				BigDecimal bd = new BigDecimal(getAttackSpeed() + 0.1);
			    bd = bd.setScale(2, RoundingMode.HALF_UP);
			    return bd.doubleValue();
			}
		}else {
			return getAttackSpeed();
		}
	}
	
	@Override
	public int getNextRange() {
		if(!fullyUpgraded()) {
			if(level == MAX_LEVEL - 1) {
				return getRange() + 25;
			}else {
				return getRange() + 10;
			}
		}else {
			return getRange();
		}
	}


	@Override
	public void addAtack() {
		if(target == null) return;
		Attack newAttack = new CannonAttack(rangeCircle.getCenterX(), rangeCircle.getCenterY(), new Attributes[0],
											level, target, this);
		attackReady = false;
		attacks.add(newAttack);
	}

	@Override
	public int getNextSplashRange() {
		if(!fullyUpgraded()) {
			if(level == MAX_LEVEL - 1) {
				return getSplashRange() + 20;
			}else {
				return getSplashRange() + 5;
			}
		}else {
			return getSplashRange();
		}
	}

	@Override
	public int getNextSplashDamage() {
		if(!fullyUpgraded()) {
			
			double ratio = BASE_SPLASH_RATIO;
			for(int i = 0; i < level; i++) {
				if(i == MAX_LEVEL - 2) {
					ratio += 0.1;
				}else {
					ratio += 0.05;
				}
			}
			return (int) (getNextDamage() * ratio);
			
		}else {
			return getSplashDamage();
		}
	}
}

package shapetd.GameObjects.Attacks;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

import shapetd.Game;
import shapetd.GameEngine.GameEngine;
import shapetd.GameObjects.GameObject;
import shapetd.GameObjects.Enemies.Enemy;
import shapetd.GameObjects.Towers.Tower;

@SuppressWarnings("serial")
public abstract class Attack extends GameObject {
	// protected Shape graphic;
	protected int damage;
	protected int speed;
	protected List<Attributes> attributes;
	boolean fired;
	Enemy target;
	Point2D.Double impactPoint;
	int length;
	int width;
	Tower sourceTower;
	Point2D.Double tip;
	double attackAngle;
	boolean impact;
	int splashRange;
	int splashDamage;

	public Attack(Shape graphic, int damage, int speed,
			Attributes[] attributes, Color color, Enemy target, int length, int width,
			Tower source, int splashRange, int splashDamage) {
		
		super(graphic, color);
		
		impact = false;
		sourceTower = source;
		this.splashRange = splashRange;
		this.splashDamage = splashDamage;
		this.damage = damage;
		this.speed = speed;
		this.attributes = Arrays.asList(attributes);
		this.fired = false;
		tip = source.getCurrentLocation();
		setTarget(target);
		angleAttackToTarget();
		
	}

	public void setGraphic(Shape shape, Point2D.Double tip) {
		this.graphic = shape;
		length = graphic.getBounds().height;
		width = graphic.getBounds().width;
		this.tip = tip;
	}
		
	//public abstract void updateGraphic(Point2D.Double tip);
	
	public Enemy getTarget() {
		return target;
	}
	
	public void setTarget(Enemy target) {
		this.target = target;
		impactPoint = calculateTargetImpact();
	}
	
	public int getSplashRange() {
		return splashRange;
	}
	
	public int getSplashDamage() {
		return splashDamage;
	}
	
	public Point2D.Double getTargetImpactPoint() {
		return impactPoint;
	}

	public Shape getGraphic() {
		return this.graphic;
	}
	
	public void setImpact() {
		impact = true;
	}
	
	public boolean impact() {
		return impact;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public boolean hitTarget() {
		if(target.isKilled()) {
			sourceTower.removeTarget();
			return false;
		}
		
		if(target.hitByAttack(this)) {
			sourceTower.removeTarget();
			return true;
		}
		return false;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Attributes[] getAttributes() {
		return (Attributes[]) this.attributes.toArray();
	}

	public void setAttributes(Attributes[] attributes) {
		this.attributes = Arrays.asList(attributes);
	}

	public void addAttribute(Attributes attribute) {
		this.attributes.add(attribute);
	}

	public boolean isFired() {
		return this.fired;
	}

	public void setFired(boolean fired) {
		this.fired = fired;
	}
	
	public Point2D.Double getTipOfAttack() {
		return tip;
	}
	
	public abstract void moveAttackTip();
		
	
	public abstract void angleAttackToTarget();
	
	public Point2D.Double calculateTargetImpact() {
		Point2D.Double curLocation = target.getCurrentLocation();
		double speed = target.getSpeed();
		Point2D.Double destLocation = target.getDestination();
		
		Point2D.Double myLocation = getTipOfAttack();
		
		double distanceToDest = Math.sqrt(Math.pow(destLocation.x - curLocation.x, 2) + Math.pow(destLocation.y - curLocation.y, 2));
		distanceToDest -= speed; //account for initial enemy movement when attack is created
		double timeToReachDest = distanceToDest / speed;
		
		double distanceToImpact = Math.sqrt(Math.pow(destLocation.x - myLocation.x, 2) + Math.pow(destLocation.y - myLocation.y, 2));
		double timeToImpact = distanceToImpact / this.speed;
		
		int n = 2;
		Point2D.Double previousDest = new Point2D.Double(curLocation.x, curLocation.y);
		while(Math.ceil(timeToImpact) > Math.ceil(timeToReachDest)) {
			
			previousDest = destLocation;
			destLocation = GameEngine.getNthDestOfEnemy(target, n, Game.getInstance().getGrid());
			n++;
			
			if(destLocation == previousDest) break;
			
			distanceToDest = Math.sqrt(Math.pow(destLocation.x - previousDest.x, 2) + Math.pow(destLocation.y - previousDest.y, 2));
			timeToReachDest += distanceToDest / speed;
			
			distanceToImpact = Math.sqrt(Math.pow(destLocation.x - myLocation.x, 2) + Math.pow(destLocation.y - myLocation.y, 2));
			timeToImpact = distanceToImpact / this.speed;
			
		}
		
		timeToReachDest -= (distanceToDest / speed);
		
		double margin = ((target.getGraphic().getBounds2D().getWidth() / 3) - 1) / speed;
		Point2D.Double closestPoint = previousDest;
		double closestTimeDif = Double.MAX_VALUE;
		double dx = (destLocation.x - previousDest.x) / 6;
		double dy = (destLocation.y - previousDest.y) / 6;
		
		double endX = destLocation.x;
		double endY = destLocation.y;
		
		double targetTime;
		
		int i = 0;
		for(i = 0; i<32; i++) {
			targetTime = Math.ceil(((Math.sqrt(Math.pow(endX - previousDest.x, 2) + Math.pow(endY - previousDest.y, 2))) / speed) + timeToReachDest);
			timeToImpact =  Math.ceil( Math.sqrt(Math.pow(endX - myLocation.x, 2) + Math.pow(endY - myLocation.y, 2)) / this.speed);
			
			if(timeToImpact < targetTime) {
				
				if(timeToImpact >= targetTime - margin) {
					closestPoint = new Point2D.Double(endX, endY);
					break;
				}
				
			}else if(timeToImpact > targetTime) {
				
				if(timeToImpact <= targetTime + margin) {
					closestPoint = new Point2D.Double(endX, endY);
					break;
				}
				
			}else {
				closestPoint = new Point2D.Double(endX, endY);
				break;
			}
			
			double dif = Math.abs(targetTime - timeToImpact);
			if(dif < closestTimeDif) {
				closestTimeDif = dif;
				closestPoint = new Point2D.Double(endX, endY);
			}
			
			endX -= dx;
			endY -= dy;
		}
		
		return closestPoint;
		
	}
	
}

package shapetd.GameObjects.Attacks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import shapetd.GameUtilities;
import shapetd.GameObjects.Enemies.Enemy;
import shapetd.GameObjects.GameObject.Attributes;
import shapetd.GameObjects.Towers.Tower;

public class CannonAttack extends Attack{
	
	final static int WIDTH = 10;
	final static int LENGTH = 10;
	
	final static int SPEED = 6;
	
	public CannonAttack(double x, double y,
			Attributes[] attributes, int level, Enemy target, Tower source) {
		
		super( new Ellipse2D.Double(x, y, WIDTH, LENGTH), source.getAttackDamage(), SPEED, attributes, Color.BLACK,
										target, WIDTH, LENGTH, source, source.getSplashRange(), source.getSplashDamage());
		
	}

	
	public void moveAttackTip() {

		double dist = GameUtilities.distanceBetweenPoints(tip, impactPoint);
		
		double tipX = tip.x, tipY = tip.y;

		double xAdd = Math.sin(attackAngle) * speed;
		double yAdd = Math.cos(attackAngle) * speed;
		
		if(dist <= speed) {
			
			tipX = impactPoint.x;
			tipY = impactPoint.y;
			impact = true;
		}		
		else {
				
			if(tip.y > impactPoint.y) {
				tipY -= yAdd; 
			}else {
				tipY += yAdd;
			}
			
			if(tip.x > impactPoint.x) {
				tipX -= xAdd;
			}else {
				tipX += xAdd;
			}
		}
		
		tip = new Point2D.Double(tipX, tipY);
		
		Ellipse2D attack = new Ellipse2D.Double(tipX - (WIDTH / 2.0), tipY - (LENGTH / 2.0), WIDTH, LENGTH);
		setGraphic(attack);
	}
	
	public void angleAttackToTarget() {
		Point2D.Double tip = getTipOfAttack();
		if(impactPoint == null) {
			impactPoint = calculateTargetImpact();
		}
		
		double ydif = Math.abs(tip.y - impactPoint.y);
		double xdif = Math.abs(tip.x - impactPoint.x);
		
		double distance = Math.sqrt( Math.pow(ydif, 2) + Math.pow(xdif, 2) );
		
		double theta = Math.acos(ydif / distance);
		attackAngle = theta;
				
		double x = tip.x - (WIDTH / 2.0);
		double y = tip.y - (LENGTH / 2.0);
		Ellipse2D attack = new Ellipse2D.Double(x, y, WIDTH, LENGTH);
		setGraphic(attack);
	}
	

	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(graphic);
	}

}

package shapetd.GameObjects.Attacks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import shapetd.GameUtilities;
import shapetd.GameObjects.Enemies.Enemy;
import shapetd.GameObjects.Towers.Tower;

@SuppressWarnings("serial")
public class ArrowAttack extends Attack {
	
	final static int WIDTH = 2;
	final static int LENGTH = 15;
	
	final static int BASE_DAMAGE = 25;
	final static int SPEED = 10;
	
	public ArrowAttack(double x, double y,
			Attributes[] attributes, int level, Enemy target, Tower source) {
		
		super(new Line2D.Double(x, y, x, y+1), source.getAttackDamage(), SPEED, attributes, Color.BLACK,
				target, WIDTH, LENGTH, source, source.getSplashRange(), source.getSplashDamage());
		
	}

	
	public void moveAttackTip() {

		double dist = GameUtilities.distanceBetweenPoints(tip, impactPoint);
		
		double tipX = tip.x, tipY = tip.y;
		double yEnd = LENGTH * Math.cos(attackAngle);
		double xEnd = LENGTH * Math.sin(attackAngle);
		double xAdd = Math.sin(attackAngle) * speed;
		double yAdd = Math.cos(attackAngle) * speed;
		
		if(dist <= speed) {
			
			if(tipX < impactPoint.x) {
				xEnd = impactPoint.x - xEnd;
			}else {
				xEnd = impactPoint.x + xEnd;
			}
			
			if(tipY < impactPoint.y) {
				yEnd = impactPoint.y - yEnd;
			}else {
				yEnd = impactPoint.y + yEnd;
			}
			
			tipX = impactPoint.x;
			tipY = impactPoint.y;
			impact = true;
		}		
		else {
				
			if(tip.y > impactPoint.y) {
				tipY -= yAdd; 
				yEnd = tipY + yEnd;
			}else {
				tipY += yAdd;
				yEnd = tipY - yEnd;
			}
			
			if(tip.x > impactPoint.x) {
				tipX -= xAdd;
				xEnd = tipX + xEnd;
			}else {
				tipX += xAdd;
				xEnd = tipX - xEnd;
			}
		}
		
		Point2D.Double endPoint = new Point2D.Double(xEnd, yEnd);
		tip = new Point2D.Double(tipX, tipY);
		Line2D.Double attack = new Line2D.Double(tip, endPoint);
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
		
		double yEnd = LENGTH * Math.cos(theta);
		double xEnd = LENGTH * Math.sin(theta);
		
		if(tip.y > impactPoint.y) {
			yEnd = tip.y + yEnd;
		}else {
			yEnd = tip.y - yEnd;
		}
		
		if(tip.x > impactPoint.x) {
			xEnd = tip.x + xEnd;
		}else {
			xEnd = tip.x - xEnd;
		}
		
		Point2D.Double endPoint = new Point2D.Double(xEnd, yEnd);
		Line2D.Double attack = new Line2D.Double(tip, endPoint);
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
		g2d.setColor(this.color);
		g2d.draw(graphic);
	}

}

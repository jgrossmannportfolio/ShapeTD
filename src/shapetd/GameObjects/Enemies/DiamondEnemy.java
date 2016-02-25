package shapetd.GameObjects.Enemies;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import shapetd.GameObjects.GameObject.Attributes;

public class DiamondEnemy extends Enemy{
	
public static final int WIDTH = 22;
public static final int LENGTH = 25;
	
	public static final int BASE_HEALTH = 100;
	
	public static final double BASE_SPEED = 0.68;

	public DiamondEnemy(Shape graphic, double speed, int health, int armor,
			Attributes[] resistances, int level, Point2D.Double destination,
			Point destindex) {
		
		super(graphic, speed, health, armor, resistances, level, destination,
				destindex);

	}

	public DiamondEnemy(double x, double y, Point2D.Double destination,
			Point destindex, int level) {
		
		this(createDiamond(x, y), calculateSpeed(level), calculateHealth(level), 0, new Attributes[0],
					level, destination, destindex);
	}
	
	public static int calculateHealth(int level) {
		int health = BASE_HEALTH;
		for(int i = 1; i<level; i++) {
			health *= 1.925;
		}
		return health;
	}
	
	public String getType() {
		return "Diamond Enemy";
	}
	
	private static double calculateSpeed(int level) {
		double speed = BASE_SPEED;
		for(int i = 1; i<level; i++) {
			speed *= 1.075;
		}
		return speed;
	}
	
	private static Shape createDiamond(double x, double y) {
		int width = WIDTH / 2;
		int length = LENGTH / 2;
		
		//int[] xPoints = {(int)x + width, (int) x + 2 * width,(int) x + width, (int)x};
		//int[] yPoints = {(int)y, (int) y + length, (int) y + length * 2, (int) y + length};
		
		Path2D diamond = new Path2D.Double();
		diamond.moveTo(x + width, y);
		diamond.lineTo(x + width * 2.0, y + length);
		diamond.lineTo(x + width, y + length * 2.0);
		diamond.lineTo(x, y + length);
		diamond.closePath();
		
		return diamond;
	}


	public void updateGraphic(double x, double y) {
		this.setGraphic(createDiamond(x, y));
	}
	

	@Override
	public int getReward() {
		//return level + 1;
		if(level < 5) {
			return level + 1;
		}else if(level > 7) {
			return (int) Math.ceil(level * 2.5);
		}
		return level * 2;
	}
}

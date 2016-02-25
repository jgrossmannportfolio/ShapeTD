package shapetd.GameObjects.Enemies;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class CircleEnemy extends Enemy {
		
	public static int DIAMETER = 25;
	
	public static final int BASE_HEALTH = 50;
	
	public static final double BASE_SPEED = 0.8;

	public CircleEnemy(Shape graphic, double speed, int health, int armor,
			Attributes[] resistances, int level, Point2D.Double destination,
			Point destindex) {
		
		super(graphic, speed, health, armor, resistances, level, destination,
				destindex);

	}
	
	public String getType() {
		return "Circle Enemy";
	}

	public CircleEnemy(double x, double y, Point2D.Double destination,
			Point destindex, int level) {
		this(new Ellipse2D.Double(x, y, DIAMETER, DIAMETER), calculateSpeed(level), calculateHealth(level) , 0, new Attributes[0],
				level, destination, destindex);
	}

	public static int calculateHealth(int level) {
		double health = BASE_HEALTH;
		for(int i = 1; i<level; i++) {
			health *= 1.9;
		}
		return (int) health;
	}
	
	private static double calculateSpeed(int level) {
		double speed = BASE_SPEED;
		for(int i = 1; i<level; i++) {
			speed *= 1.075;
		}
		return speed;
	}
	
	/*
	 * public CircleEnemy() { this((double)new
	 * Random().nextInt(SCREEN_DIMENSIONS.width), 0.0, new Point2D.Double()); }
	 */

	public void updateGraphic(double x, double y) {
		((Ellipse2D)graphic).setFrame(x, y, DIAMETER, DIAMETER);
		//this.setGraphic(new Ellipse2D.Double(x, y, 20, 20));
	}

	
	@Override
	public int getReward() {
		if(level < 6) {
			return ((level-1) / 2) + 1;
		}else {
			return level;
		}
	}

}

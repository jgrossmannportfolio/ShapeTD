package shapetd.GameObjects.Enemies;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import shapetd.GameObjects.GameObject.Attributes;

public class SquareEnemy extends Enemy {
	
	public static int WIDTH = 25;
	
	public static final int BASE_HEALTH = 65;
	
	public static final double BASE_SPEED = 0.92;

	public SquareEnemy(Shape graphic, double speed, int health, int armor,
			Attributes[] resistances, int level, Point2D.Double destination,
			Point destindex) {
		
		super(graphic, speed, health, armor, resistances, level, destination,
				destindex);

	}

	public SquareEnemy(double x, double y, Point2D.Double destination,
			Point destindex, int level) {
		
		this(new Rectangle2D.Double(x, y, WIDTH, WIDTH), calculateSpeed(level), calculateHealth(level), 0, new Attributes[0],
					level, destination, destindex);
	}
	
	public String getType() {
		return "Square Enemy";
	}
	
	public static int calculateHealth(int level) {
		int health = BASE_HEALTH;
		for(int i = 1; i<level; i++) {
			health *= 1.9;
		}
		return health;
	}
	
	private static double calculateSpeed(int level) {
		double speed = BASE_SPEED;
		for(int i = 1; i<level; i++) {
			speed *= 1.075;
		}
		return speed;
	}


	public void updateGraphic(double x, double y) {
		this.setGraphic(new Rectangle2D.Double(x, y, WIDTH, WIDTH));
	}
	

	@Override
	public int getReward() {
		if(level < 6) {
			return (int) ((level - 1)/ 1.5) + 1;
		}else {
			return level + 1;
		}
	}
}

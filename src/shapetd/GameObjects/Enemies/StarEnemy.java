package shapetd.GameObjects.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import shapetd.GameUtilities;
import shapetd.GameObjects.GameObject.Attributes;

public class StarEnemy extends Enemy {
	public static final int SIDE = 10;
	
	public static final double WIDTH = (2.0 * SIDE) + (2.0 * SIDE * Math.sin((Math.PI * 18.0) / 180.0));
	
	public static final int BASE_HEALTH = 500;
	
	public static final double BASE_SPEED = 0.6;

	public StarEnemy(Shape graphic, double speed, int health, int armor,
			Attributes[] resistances, int level, Point2D.Double destination,
			Point destindex) {
		
		super(graphic, speed, health, armor, resistances, level, destination,
				destindex);

	}

	public StarEnemy(double x, double y, Point2D.Double destination,
			Point destindex, int level) {
		
		this(createStar(x, y), calculateSpeed(level), calculateHealth(level), 0, new Attributes[0],
					level, destination, destindex);
	}
	
	public static int calculateHealth(int level) {
		int health = BASE_HEALTH;
		if(level == 10) return 325000;
		for(int i = 1; i<level; i++) {
			health *= 2.15;
		}
		return health;
	}
	
	private static double calculateSpeed(int level) {
		double speed = BASE_SPEED;
		/*for(int i = 1; i<level; i++) {
			speed *= 1.025;
		}*/
		return speed;
	}
	
	public String getType() {
		return "Star Enemy (BOSS)";
	}
	
	public static Shape createStar(double x, double y) {
		double width = WIDTH / 2;
		double deg18 = GameUtilities.degToRad(18.0);
		
		double bottomSide = 2.0 * SIDE * Math.sin(deg18);
		double heightTriangle = SIDE * Math.cos(deg18);
		
		double curX = x + width;
		double curY = y;
		
		Path2D star = new Path2D.Double();
		star.moveTo(curX, curY);
		
		curX += bottomSide / 2.0;
		curY += heightTriangle;
		star.lineTo(curX, curY);
		
		curX += SIDE;
		star.lineTo(curX, curY);
		
		curX = x + width;
		curX += (SIDE + bottomSide) * Math.sin(deg18);
		curY = y + (SIDE + bottomSide) * Math.cos(deg18);
		star.lineTo(curX, curY);
		
		curX = x + width;
		curX += (2.0 * SIDE + bottomSide) * Math.sin(deg18);
		curY = y + (2.0 * SIDE + bottomSide) * Math.cos(deg18);
		star.lineTo(curX, curY);
		
		curY -= Math.sqrt( Math.pow(SIDE, 2) - Math.pow((x + width) - curX, 2) );
		curX = x + width;
		star.lineTo(curX, curY);
		
		curX = x + width;
		curX -= (2.0 * SIDE + bottomSide) * Math.sin(deg18);
		curY = y + (2.0 * SIDE + bottomSide) * Math.cos(deg18);
		star.lineTo(curX, curY);
		
		curX = x + width;
		curX -= (SIDE + bottomSide) * Math.sin(deg18);
		curY = y + (SIDE + bottomSide) * Math.cos(deg18);
		star.lineTo(curX, curY);
		
		curX = x + width;
		curX -= bottomSide / 2.0;
		curX -= SIDE;
		curY = y + heightTriangle;
		star.lineTo(curX, curY);
		
		curX += SIDE;
		star.lineTo(curX, curY);
			
		star.closePath();
		
		return star;
	}


	public void updateGraphic(double x, double y) {
		this.setGraphic(createStar(x, y));
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(graphic);
		
		g2d.setColor(Color.BLACK);
		g2d.draw(graphic);
		
		if(selected) {
			paintFocus(g2d, graphic, 4);
		}
	}
	

	@Override
	public int getReward() {
		return 15 * level;
	}
}

package shapetd;

import java.awt.geom.Point2D;

public class GameUtilities {
	
	public static double distanceBetweenPoints(Point2D.Double p1, Point2D.Double p2) {
		return Math.sqrt( Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2) );
	}
	
	public static double degToRad(double deg) {
		return (Math.PI * deg) / 180.0;
	}
}

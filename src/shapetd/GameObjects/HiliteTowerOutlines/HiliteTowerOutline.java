package shapetd.GameObjects.HiliteTowerOutlines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import javafx.scene.shape.Ellipse;
import shapetd.Node;
import shapetd.GameObjects.Colors;
import shapetd.GameObjects.GameObject;
import shapetd.GameObjects.Towers.Tower;

public abstract class HiliteTowerOutline extends GameObject{
	protected Node[] squares;
	protected Point2D.Double location;
	Shape range;
	
	public HiliteTowerOutline(Point2D.Double location, Node[] squares, Color color) {
		super(new RoundRectangle2D.Double(-Tower.TOWER_WIDTH, -Tower.TOWER_WIDTH,
				Tower.TOWER_WIDTH, Tower.TOWER_WIDTH, 8.0, 8.0), color);
		this.location = location;
		this.squares = squares;
		range = new Ellipse2D.Double(location.x, location.y, 240, 240);
	}
	
	public HiliteTowerOutline(Color color) {
		super(new RoundRectangle2D.Double(-Tower.TOWER_WIDTH, 
				-Tower.TOWER_WIDTH, Tower.TOWER_WIDTH, Tower.TOWER_WIDTH, 8.0, 8.0), color);
	}
	
	public Node[] getSquares() {
		return this.squares;
	}
	public void setSquares(Node[] squares) {
		this.squares = squares;
	}
	
	public Point2D.Double getLocation() {
		return this.location;
	}
	public void setLocation(Point2D.Double location) {
		this.location = location;
	}
	
	public void updateGraphic() {
		setGraphic(new RoundRectangle2D.Double(this.location.x, this.location.y, 
				Tower.TOWER_WIDTH, Tower.TOWER_WIDTH, 8.0, 8.0));
		range = new Ellipse2D.Double(location.x-120+(Tower.TOWER_WIDTH/2), location.y-120+(Tower.TOWER_WIDTH/2), 240, 240);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if(range != null) {
			g2d.setColor(Colors.tower_range);
			g2d.fill(range);
		}
		g2d.setColor(this.color);
		g2d.fill(this.graphic);
		
	}
}

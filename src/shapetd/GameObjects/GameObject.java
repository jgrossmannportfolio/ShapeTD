package shapetd.GameObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public abstract class GameObject{ 
	protected Shape graphic;
	protected Color color;
	protected boolean drawn;

	public static final Dimension SCREEN_DIMENSIONS = Toolkit
			.getDefaultToolkit().getScreenSize();

	public enum Attributes {
		// special abilities/attributes for an attack
		SLOW, SPLASH, PIERCE
	}

	public GameObject(Shape graphic, Color color) {
		this.graphic = graphic;
		this.color = color;
		this.drawn = false;
	}

	public void setGraphic(Shape shape) {
		this.graphic = shape;
	}

	public Shape getGraphic() {
		return this.graphic;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void setDrawn(boolean drawn) {
		this.drawn = drawn;
	}

	public boolean isDrawn() {
		return this.drawn;
	}

	
	public Point2D.Double getCurrentLocation() {
		return new Point2D.Double(graphic.getBounds2D().getCenterX(), graphic.getBounds2D().getCenterY());
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.color);
		g2d.fill(this.graphic);
	}
}

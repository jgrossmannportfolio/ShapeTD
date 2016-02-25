package shapetd.GameObjects.Enemies;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import shapetd.GameUtilities;
import shapetd.GameObjects.Colors;
import shapetd.GameObjects.GameObject;
import shapetd.GameObjects.Attacks.Attack;

@SuppressWarnings("serial")
public abstract class Enemy extends GameObject {
	// protected RectangularShape graphic;
	protected double speed;
	protected volatile int health;
	protected int armor;
	protected List<Attributes> resistances;
	protected int level;
	protected Point2D.Double destination;
	volatile boolean killed;
	int originalHealth;
	boolean selected;

	// index of destination grid in grid array
	protected Point destindex;

	public Enemy(Shape graphic, double speed, int health, int armor,
			Attributes[] resistances, int level, Point2D.Double destination,
			Point destindex) {
		super(graphic, Colors.getLevelColor(level));
		this.graphic = graphic;
		this.speed = speed;
		this.health = health;
		originalHealth = health;
		this.armor = armor;
		this.resistances = Arrays.asList(resistances);
		this.level = level;
		this.destination = destination;
		this.destindex = destindex;
		killed = false;
		selected = false;
	}

	/*
	 * public void setGraphic(RectangularShape shape) { this.graphic = shape; }
	 * public RectangularShape getGraphic(){ return this.graphic; }
	 */

	// speed getter/setter
	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void select(boolean select) {
		selected = select;
	}
	
	public int getOriginalHealth() {
		return originalHealth;
	}
	
	public abstract int getReward();

	// health getter/setter
	public int getHealth() {
		return this.health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean isKilled() {
		return killed;
	}
	
	public void killEnemy() {
		killed = true;
	}
	
	public abstract String getType();
	
	public boolean hitBySplash(Attack attack) {
		if(killed) return false;
		
		int splash = attack.getSplashRange();
		double distance = GameUtilities.distanceBetweenPoints(attack.getTipOfAttack(), getCurrentLocation());
		distance -= graphic.getBounds2D().getWidth();
		
		if(distance > splash) {
			//System.out.println("Attack missed enemy");
			return false;
		}
		
		health -= attack.getSplashDamage();
		if(health <= 0) {
			killed = true;
			return true;
		}
		return false;
	}
	
	public boolean hitByAttack(Attack attack) {
		if(killed) return false;
		if(!attack.getGraphic().intersects(graphic.getBounds2D())) {
			//System.out.println("Attack missed enemy");
			return false;
		}
		health -= attack.getDamage();
		if(health <= 0) {
			killed = true;
			return true;
		}
		return false;
	}

	// armor getter/setter
	public int getArmor() {
		return this.armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	// resistances getter/setter
	public Attributes[] getResistances() {
		return (Attributes[]) this.resistances.toArray();
	}

	public void setResistances(Attributes[] res) {
		this.resistances = Arrays.asList(res);
	}

	public void addResistance(Attributes res) {
		this.resistances.add(res);
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Point2D.Double getDestination() {
		return this.destination;
	}

	public void setDestination(Point2D.Double dest) {
		this.destination = dest;
	}

	public Point getDestinationIndex() {
		return this.destindex;
	}

	public void setDestinationIndex(Point index) {
		this.destindex = index;
	}
	
	 public static void paintFocus(Graphics2D g,Shape shape,int biggestStroke) {
	    Color focusColor = Colors.getFocusRingColor();
	    Color[] focusArray = new Color[] {
	      new Color(focusColor.getRed(), focusColor.getGreen(), focusColor.getBlue(),255),
	      new Color(focusColor.getRed(), focusColor.getGreen(), focusColor.getBlue(),170),
	      new Color(focusColor.getRed(), focusColor.getGreen(), focusColor.getBlue(),110) 
	    };
	    g.setStroke(new BasicStroke(biggestStroke));
	    g.setColor(focusArray[2]);
	    g.draw(shape);
	    g.setStroke(new BasicStroke(biggestStroke-1));
	    g.setColor(focusArray[1]);
	    g.draw(shape);
	    g.setStroke(new BasicStroke(biggestStroke-2));
	    g.setColor(focusArray[0]);
	    g.draw(shape);
	    g.setStroke(new BasicStroke(1));
	 }

	public void drawSelectedOutline(Graphics2D g2d) {
		g2d.setColor(Color.YELLOW);
	}

	public abstract void updateGraphic(double x, double y);
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.color);
		g2d.fill(this.graphic);
		
		if(selected) {
			paintFocus(g2d, graphic, 4);
		}
	}
}

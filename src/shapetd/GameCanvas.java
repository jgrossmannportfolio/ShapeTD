package shapetd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

	public GameCanvas() {
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		addMouseListener(this);
		
		setDoubleBuffered(true);
	}

	// Override to do custom drawing
	// called by repaint()
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; // Java 2D
		super.paintComponent(g2d); // paint background
		setBackground(Color.WHITE); // Use image if desired

		// draw game objects
		Game.gameDraw(g2d);
	}

	// Key Events
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	// Mouse Events
	@Override
	public void mouseClicked(MouseEvent e) {
		//Game.gameMouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Game.gameMouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Game.gameMouseMoved(e);
		
	}

}

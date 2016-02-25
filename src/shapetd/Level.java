package shapetd;

import java.util.List;

import shapetd.GameObjects.Enemies.Enemy;

public class Level {
	private int number;
	private List<Enemy> enemies;
	
	public Level(int number, List<Enemy> enemies) {
		this.number = number;
		this.enemies = enemies;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public int getLevelNumber() {
		return number;
	}
}

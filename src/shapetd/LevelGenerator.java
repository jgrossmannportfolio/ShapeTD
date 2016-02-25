package shapetd;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import shapetd.GameObjects.Enemies.CircleEnemy;
import shapetd.GameObjects.Enemies.DiamondEnemy;
import shapetd.GameObjects.Enemies.Enemy;
import shapetd.GameObjects.Enemies.SquareEnemy;
import shapetd.GameObjects.Enemies.StarEnemy;
import shapetd.GameObjects.Enemies.TriangleEnemy;

public class LevelGenerator {
	
	public static final int SPAWN_HEIGHT = 500;

	public static Level[] generateLevels(GameGrid grid) {
		Level[] levels = new Level[Game.NUMBER_OF_LEVELS];
		int maxEnemyLevel = (int) Math.ceil(Game.NUMBER_OF_LEVELS / 2.0);
		
		if(Game.NUMBER_OF_LEVELS > 0) {
			levels[0] = getLevel0(grid);
		}
		
		for(int enemyLevel = 1; enemyLevel <= maxEnemyLevel; enemyLevel++) {
			
			for(int level = 1; level <= 5; level++) {
				
				int currentLevel = ((enemyLevel - 1) * 5) + level;
				if(currentLevel >= Game.NUMBER_OF_LEVELS) break;
				
				if(level == 1) {
					levels[currentLevel] = getCircleLevel(grid, currentLevel, enemyLevel);
				}
				else if(level == 2) {
					levels[currentLevel] = getSquareLevel(grid, currentLevel, enemyLevel);
				}
				else if(level == 3) {
					levels[currentLevel] = getDiamondLevel(grid, currentLevel, enemyLevel);
				}
				else if(level == 4) {
					levels[currentLevel] = getTriangleLevel(grid, currentLevel, enemyLevel);
				}
				else if(level == 5) {
					levels[currentLevel] = getStarLevel(grid, currentLevel, enemyLevel);
				}
			}
		}
		
		return levels;
	}
	
	private static Level getLevel0(GameGrid grid) {
		List<Enemy> enemies = new ArrayList<Enemy>(20);
		for(int i = 0; i<20; i++) {
			int width = ThreadLocalRandom.current().nextInt(CircleEnemy.DIAMETER + 1, Game.CANVAS_WIDTH - CircleEnemy.DIAMETER);
			int height = -1 * ThreadLocalRandom.current().nextInt(20, SPAWN_HEIGHT);
			Point2D.Double point = grid.findDestination(width, -10);
			Point p = grid.findDestinationIndex(point);
			enemies.add(new CircleEnemy(width, height, point, p, 1));
		}
		
		return new Level(0, enemies);
	}
	
	private static Level getCircleLevel(GameGrid grid, int level, int enemyLevel) {
		List<Enemy> enemies = new ArrayList<Enemy>(30);
		for(int i = 0; i<30; i++) {
			int width = ThreadLocalRandom.current().nextInt(CircleEnemy.DIAMETER + 1, Game.CANVAS_WIDTH - CircleEnemy.DIAMETER);
			int height = -1 * ThreadLocalRandom.current().nextInt(20, SPAWN_HEIGHT);
			Point2D.Double point = grid.findDestination(width, -10);
			Point p = grid.findDestinationIndex(point);
			enemies.add(new CircleEnemy(width, height, point, p, enemyLevel));
		}
		
		return new Level(level, enemies);
	}
	
	private static Level getSquareLevel(GameGrid grid, int level, int enemyLevel) {
		List<Enemy> enemies = new ArrayList<Enemy>(30);
		for(int i = 0; i<30; i++) {
			int width = ThreadLocalRandom.current().nextInt(SquareEnemy.WIDTH + 1, Game.CANVAS_WIDTH - SquareEnemy.WIDTH);
			int height = -1 * ThreadLocalRandom.current().nextInt(20, SPAWN_HEIGHT);
			Point2D.Double point = grid.findDestination(width, -10);
			Point p = grid.findDestinationIndex(point);
			enemies.add(new SquareEnemy(width, height, point, p, enemyLevel));
		}
		
		return new Level(level, enemies);
	}
	
	private static Level getDiamondLevel(GameGrid grid, int level, int enemyLevel) {
		List<Enemy> enemies = new ArrayList<Enemy>(25);
		for(int i = 0; i<25; i++) {
			int width = ThreadLocalRandom.current().nextInt(DiamondEnemy.WIDTH + 1, Game.CANVAS_WIDTH - DiamondEnemy.WIDTH);
			int height = -1 * ThreadLocalRandom.current().nextInt(20, SPAWN_HEIGHT);
			Point2D.Double point = grid.findDestination(width, -10);
			Point p = grid.findDestinationIndex(point);
			enemies.add(new DiamondEnemy(width, height, point, p, enemyLevel));
		}
		
		return new Level(level, enemies);
	}
	
	private static Level getTriangleLevel(GameGrid grid, int level, int enemyLevel) {
		List<Enemy> enemies = new ArrayList<Enemy>(30);
		for(int i = 0; i<30; i++) {
			int width = ThreadLocalRandom.current().nextInt(TriangleEnemy.WIDTH + 1, Game.CANVAS_WIDTH - TriangleEnemy.WIDTH);
			int height = -1 * ThreadLocalRandom.current().nextInt(20, SPAWN_HEIGHT);
			Point2D.Double point = grid.findDestination(width, -10);
			Point p = grid.findDestinationIndex(point);
			enemies.add(new TriangleEnemy(width, height, point, p, enemyLevel));
		}
		
		return new Level(level, enemies);
	}
	
	private static Level getStarLevel(GameGrid grid, int level, int enemyLevel) {
		List<Enemy> enemies = new ArrayList<Enemy>(5);
		for(int i = 0; i<5; i++) {
			int width = ThreadLocalRandom.current().nextInt((int)StarEnemy.WIDTH + 1, (int) (Game.CANVAS_WIDTH - StarEnemy.WIDTH));
			int height = -1 * ThreadLocalRandom.current().nextInt(20, SPAWN_HEIGHT);
			Point2D.Double point = grid.findDestination(width, -10);
			Point p = grid.findDestinationIndex(point);
			enemies.add(new StarEnemy(width, height, point, p, enemyLevel));
		}
		
		return new Level(level, enemies);
	}
	
	
}

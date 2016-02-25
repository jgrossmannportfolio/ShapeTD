package shapetd.GameEngine;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import shapetd.Game;
import shapetd.GameGrid;
import shapetd.GameGrid.GridDirections;
import shapetd.GameUtilities;
import shapetd.Node;
import shapetd.GameObjects.GameObject;
import shapetd.GameObjects.Attacks.Attack;
import shapetd.GameObjects.Enemies.Enemy;
import shapetd.GameObjects.HiliteTowerOutlines.HiliteArrowTowerOutline;
import shapetd.GameObjects.HiliteTowerOutlines.HiliteCannonTowerOutline;
import shapetd.GameObjects.HiliteTowerOutlines.HiliteTowerOutline;
import shapetd.GameObjects.Towers.ArrowTower;
import shapetd.GameObjects.Towers.CannonTower;
import shapetd.GameObjects.Towers.Tower;

public class GameEngine {

	public GameEngine() {
	};

	public void updateAttacks(List<Attack> attacks) {

	}

	public void updateTowers(List<Tower> towers, List<Enemy> enemies) {
		
		for(Tower tower : towers) {
			
			List<Attack> removeAttacks = new ArrayList<Attack>();
			for(Attack attack : tower.getAttacks()) {
				
				if(attack.impact()) {
					removeAttacks.add(attack);
					Enemy enemy = attack.getTarget();
					boolean enemyKilled = attack.hitTarget();
					
					Game.getInstance().updateEnemyInfo(enemy);
					
					if(enemyKilled) {
						Game.getInstance().removeEnemy(attack.getTarget());
					}
					//means enemy died before hit could process
					else if(enemy.isKilled()){
						
						enemy = findAlternateEnemyHit(enemies, attack.getTipOfAttack());
						if(enemy == null) continue;
						
						tower.setTarget(enemy);
						attack.setTarget(enemy);
						enemyKilled = attack.hitTarget();
						Game.getInstance().updateEnemyInfo(enemy);
						
						if(enemyKilled) {
							Game.getInstance().removeEnemy(enemy);
						}		
					}
					
					if(attack.getSplashRange() > 0) {
						splashDamage(enemies, attack);
					}
					
				}else {
					attack.moveAttackTip();	
				}
			}
			
			for(Attack attack : removeAttacks) tower.removeAttack(attack);
			
			if(tower.updateAttackTimer(Game.UPDATE_PERIOD_SEC)) {
				if(!tower.hasTarget()) {
					Enemy target = findTarget(tower, enemies);
					tower.setTarget(target);
				}
				tower.addAtack();
			}
		}
	}
	
	public static void splashDamage(List<Enemy> enemies, Attack attack) {
		
		for(Enemy e : new ArrayList<Enemy>(enemies)) {
			boolean enemyKilled = e.hitBySplash(attack);
			
			Game.getInstance().updateEnemyInfo(e);
			
			if(enemyKilled) {
				Game.getInstance().removeEnemy(e);
			}
		}
		
	}
	
	public static Enemy findAlternateEnemyHit(List<Enemy> enemies, Point2D impact) {
		for(Enemy enemy : enemies) {
			if(enemy.isKilled()) continue;
			if(enemy.getGraphic().getBounds2D().contains(impact)) return enemy;
		}
		return null;
	}
	
	public static Enemy findTarget(Tower tower, List<Enemy> enemies) {
		Enemy lowest = null;
		Shape range = tower.getRangeCircle();
		for(Enemy enemy : enemies) {
			
			if(range.intersects(enemy.getGraphic().getBounds2D())) {
				if(lowest == null) {
					lowest = enemy;
				}else {
					if(lowest.getCurrentLocation().y < enemy.getCurrentLocation().y) {
						lowest = enemy;
					}
				}
			}
		}
		
		return lowest;
	}
	
	public static Point2D.Double getNthDestOfEnemy(Enemy enemy, int n, GameGrid grid) {
		if(n == 0) {
			Point2D.Double loc = enemy.getCurrentLocation();
			return new Point2D.Double(loc.x, loc.y);
		}
		
		Node[][] nodegrid = grid.getPathGrid();		
		Node nextsquare = null;		
			
		int nextx = enemy.getDestinationIndex().x;
		int nexty = enemy.getDestinationIndex().y;
		
		Node curNode = nodegrid[nextx][nexty];
		for(int iteration = 1; iteration < n; iteration++) {
						
			if(curNode.getDirection() == GridDirections.GOAL) {
				return curNode.getLocation();
			} 
			else {
				nextsquare = curNode.getNext();
				
				int destx, desty;
				if(nextsquare == null) {
					for(int i = -1; i<2; i++) {
						for(int j = -1; j<2; j++) {
							
							if(i == 0 && j == 0) continue;
							destx = curNode.getIndexes().x + i;
							desty = curNode.getIndexes().y + j;
							
							if(destx >= GameGrid.num_grid_squares || desty >= GameGrid.num_grid_squares) continue;
							if(destx < 0 || desty < 0) continue;
							
							nextsquare = nodegrid[destx][desty];
							if(nextsquare.getNext() != null) break;
							
						}
						if(nextsquare.getNext() != null) break;
					}
				}
				
				curNode = nextsquare;
			}
		}
		
		return curNode.getLocation();
	}

	// POSSIBLE OPTIMIZATION HERE TO SPEED UP NEXT LOCATION
	@SuppressWarnings("static-access")
	public void updateEnemies(List<Enemy> enemies, GameGrid grid) {
		
		Node[][] nodegrid = grid.getPathGrid();
		
		double x, y, speed, nextx, nexty, mag, angle, newx, newy, ydif, xdif;
		List<Enemy> remove = new ArrayList<Enemy>();
		Node nextsquare = null;
		Rectangle2D bounds;
		
		for (Enemy enemy : enemies) {
			
			bounds = enemy.getGraphic().getBounds2D();
			x = bounds.getCenterX();
			y = bounds.getCenterY();
			speed = enemy.getSpeed();
			nextx = enemy.getDestination().x;
			nexty = enemy.getDestination().y;
			ydif = nexty - y;
			xdif = nextx - x;
			
			mag = Math.sqrt( xdif*xdif + ydif*ydif);
			if (speed >= mag) {

				Point destIndex = enemy.getDestinationIndex();
				
				// if end of grid reached
				if(nodegrid[destIndex.x][destIndex.y].getDirection() == GridDirections.GOAL) {
					remove.add(enemy);
					//Game.getInstance().removeEnemy(enemy);
					continue;
				}

				// need to use upper left corner point to create new shape
				newx = xdif + bounds.getX();
				newy = ydif + bounds.getY();
				// move the enemy to new point
				enemy.updateGraphic(newx, newy);
				
				
				int destx, desty;
				destx = enemy.getDestinationIndex().x;
				desty = enemy.getDestinationIndex().y;
				nextsquare = nodegrid[destx][desty].getNext();
				
				if(nextsquare == null) {
					for(int i = -1; i<2; i++) {
						for(int j = -1; j<2; j++) {
							if(i == 0 && j == 0) continue;
							destx = enemy.getDestinationIndex().x + i;
							desty = enemy.getDestinationIndex().y + j;
							
							if(destx >= GameGrid.num_grid_squares || desty >= GameGrid.num_grid_squares) continue;
							if(destx < 0 || desty < 0) continue;
							
							nextsquare = nodegrid[destx][desty];
							if(nextsquare.getNext() != null) break;
						}
						if(nextsquare == null) continue;
						if(nextsquare.getNext() != null) break;
					}
					if(nextsquare == null) {
						//did not find next so towers must be blocking all paths
						nextsquare = nodegrid[enemy.getDestinationIndex().x][enemy.getDestinationIndex().y+1];
					}
				}
				
				enemy.setDestination(nextsquare.getLocation());
				enemy.setDestinationIndex(nextsquare.getIndexes());
				
				speed -= mag;
				nextx = enemy.getDestination().x;
				nexty = enemy.getDestination().y;
				int xsign, ysign;
				angle = Math.atan(Math.abs(nexty - newy) / Math.abs(nextx - newx));
				if((nextx - newx) < 0) {
					xsign = -1;
				}else {
					xsign = 1;
				}
				
				if((nexty - newy) < 0) {
					ysign = -1;
				}else {
					ysign = 1;
				}
				newx = ((Math.cos(angle) * speed) * xsign) + newx;
				newy = ((Math.sin(angle) * speed) * ysign) + newy;

				// move enemy to new point
				enemy.updateGraphic(newx, newy);
				

			} else {
				int xsign, ysign;
				angle = Math.atan(Math.abs(nexty - y) / Math.abs(nextx - x));
				if((nextx - x) < 0) {
					xsign = -1;
				}else {
					xsign = 1;
				}
				
				if((nexty - y) < 0) {
					ysign = -1;
				}else {
					ysign = 1;
				}
				newx = ((Math.cos(angle) * speed) * xsign) + bounds.getX();
				newy = ((Math.sin(angle) * speed) * ysign) + bounds.getY();
				
				// move enemy to new point
				enemy.updateGraphic(newx, newy);
			}
		}
		
		for (Enemy enemy : remove) {
			// add in a penalty for letting enemy reach finish
			Game.getInstance().removeEnemy(enemy);
			//enemies.remove(enemy);
		}
	}

	public GameObject mouseClicked(int x, int y, List<Tower> towers, List<Enemy> enemies) {
		for (Tower tower : towers) {
			if (tower.getGraphic().getBounds2D().contains(x, y)) {
				return tower;
			}
		}
		
		for(Enemy e : enemies) {
			if(e.getGraphic().getBounds2D().contains(x, y)) {
				return e;
			}
		}
		
		return null;
	}
	
	
	public boolean placeTower(HiliteTowerOutline outline, GameGrid grid) {
		List<Tower> towers = Game.getInstance().getTowers();
		List<Enemy> enemies = Game.getInstance().getEnemies();
		
		Point2D.Double point = outline.getLocation();
		Point[] squares = new Point[4];
		int i = 0;
		for(Node n : outline.getSquares()) {
			squares[i++] = n.getIndexes();
		}
		
		
		Tower t;
		if(outline instanceof HiliteArrowTowerOutline) {
			t = new ArrowTower(point.x, point.y, squares);
		}else if(outline instanceof HiliteCannonTowerOutline){
			t = new CannonTower(point.x, point.y, squares);
		}else {
			return false;
		}
		
		for(Tower tower : towers ) {
			if(t.getGraphic().intersects((Rectangle2D)tower.getGraphic().getBounds2D())) {
				return false;
			}
		}
		
		for(Enemy enemy : enemies) {
			if(t.getGraphic().intersects((Rectangle2D)enemy.getGraphic().getBounds2D())) {
				return false;
			}
		}
		
		Game.getInstance().addTower(t);
		
		grid.updatePaths(Game.getInstance().getTowers());
		return true;
	}
}

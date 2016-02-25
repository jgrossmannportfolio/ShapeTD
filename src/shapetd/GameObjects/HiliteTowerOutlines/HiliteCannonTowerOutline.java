package shapetd.GameObjects.HiliteTowerOutlines;

import java.awt.geom.Point2D;

import shapetd.Node;
import shapetd.GameObjects.Colors;

public class HiliteCannonTowerOutline extends HiliteTowerOutline{

	public HiliteCannonTowerOutline(Point2D.Double location, Node[] squares) {
		super(location, squares, Colors.hilite_cannon_tower);
	}
	
	public HiliteCannonTowerOutline() {
		super(Colors.hilite_cannon_tower);
	}
}

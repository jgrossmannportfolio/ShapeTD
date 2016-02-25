package shapetd.GameObjects.HiliteTowerOutlines;

import java.awt.Color;
import java.awt.geom.Point2D;

import shapetd.Node;
import shapetd.GameObjects.Colors;

public class HiliteArrowTowerOutline extends HiliteTowerOutline{
	
	public HiliteArrowTowerOutline(Point2D.Double location, Node[] squares) {
		super(location, squares, Colors.hilite_arrow_tower);
	}
	
	public HiliteArrowTowerOutline() {
		super(Colors.hilite_arrow_tower);
	}
}

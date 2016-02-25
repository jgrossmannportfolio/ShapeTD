package shapetd;

import java.util.Comparator;

public class NodeDistanceComparator implements Comparator<DistanceNode> {

	@Override
	public int compare(DistanceNode n1, DistanceNode n2) {
		double d1, d2;
		d1 = n1.getDistance();
		d2 = n2.getDistance();
		return Double.compare(d1, d2);
	}

}

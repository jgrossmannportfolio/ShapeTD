package shapetd.GameObjects;

import java.awt.Color;

public class Colors {
	public static Color pink = Color.pink;
	public static Color yellow = Color.yellow;
	public static Color limegreen = new Color(0, 255, 0);
	public static Color blue = Color.blue;
	public static Color purple = new Color(128, 0, 128);
	public static Color green = new Color(0, 128, 0);
	public static Color orange = Color.orange;
	public static Color red = Color.red;
	public static Color brown = new Color(139, 69, 18);
	public static Color black = Color.black;
	public static Color teal = new Color(0, 255, 255);
	public static Color hilite_arrow_tower = new Color(0, 128, 0, 60);
	public static Color hilite_cannon_tower = new Color(0, 0, 128, 60);
	public static Color tower_range = new Color(0, 0, 255, 20);
	
	  public static Color getFocusRingColor() {
	    return new Color(64,113,167);
	  }
	  

	public static final Color[] Level_Colors = { Colors.pink, Colors.yellow,
			Colors.limegreen, Colors.blue, Colors.purple, Colors.green,
			Colors.orange, Colors.red, Colors.brown, Colors.black };
	
	public static final Color[] Tower_Level_Colors = {
			Colors.red, Colors.purple, Colors.black
	};
	
	public static Color getTowerLevelColor(int level) {
		if(level > 4) return null;
		if(level == 1) return null;
		return Tower_Level_Colors[level - 2];
	}

	public static Color getLevelColor(int level) {
		if(level > 10) level = 10;
		return Level_Colors[level - 1];
	}

}

package zyx.engine.components.screen.image;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.utils.cheats.Print;
import zyx.utils.geometry.Rectangle;

class Scale9Grids
{

	private static final HashMap<String, Rectangle> CACHE = new HashMap<>();

	private static final String BTN_DOWN = "BtnDown";
	private static final String BTN_HOVER = "BtnHover";
	private static final String BTN_UP = "BtnUp";
	private static final String FLAT_BG = "flat_bg";
	private static final String SAMPLE = "sample";
	
	private static final String PANEL_1_A = "panel_01_center";
	private static final String PANEL_1_B = "panel_01_downright";
	private static final String PANEL_1_C = "panel_01_upleft";
	
	private static final String PANEL_2_A = "panel_02_center";
	private static final String PANEL_2_B = "panel_02_downright";
	private static final String PANEL_2_C = "panel_02_upleft";

	public static Rectangle getGridFor(String name)
	{
		if (CACHE.containsKey(name) == false)
		{
			createEntry(name);
		}

		return CACHE.get(name);
	}

	private static void createEntry(String name)
	{
		Rectangle grid;
		String[] keys;
		
		switch (name)
		{
			case BTN_DOWN:
			case BTN_HOVER:
			case BTN_UP:
			case FLAT_BG:
			case SAMPLE:
				grid = new Rectangle(25, 25, 14, 14);
				keys = new String[] {BTN_DOWN, BTN_HOVER, BTN_UP, FLAT_BG, SAMPLE};
				break;
			case PANEL_1_A:
			case PANEL_1_B:
			case PANEL_1_C:
				grid = new Rectangle(12, 12, 8, 8);
				keys = new String[] {PANEL_1_A, PANEL_1_B, PANEL_1_C};
				break;
			case PANEL_2_A:
			case PANEL_2_B:
			case PANEL_2_C:
				grid = new Rectangle(6, 6, 4, 4);
				keys = new String[] {PANEL_2_A, PANEL_2_B, PANEL_2_C};
				break;
			default:
				grid = new Rectangle(22, 22, 20, 20);
				keys = new String[] {name};
				Print.out("[Warning] Unknown Scale9Grid name:", name);
		}
		
		for (String key : keys)
		{
			CACHE.put(key, grid);
		}
	}
}

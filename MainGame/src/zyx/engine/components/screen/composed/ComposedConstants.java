package zyx.engine.components.screen.composed;

import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.Color;

public class ComposedConstants
{

	private static final String STYLE_PANEL_01 = "panel_01";
	private static final String STYLE_PANEL_02 = "panel_02";

	private static final String COLOR_GREEN = "green";

	private static final HashMap<String, String[]> TEXTURE_MAP = new HashMap<>();
	private static final HashMap<String, Vector3f[]> IMAGE_COLOR_MAP = new HashMap<>();
	private static final HashMap<String, ComposedButtonColorMap> BUTTON_COLOR_MAP = new HashMap<>();

	static
	{
		String[] panel_01_Textures = new String[]
		{
			"panel_01_upleft", "panel_01_downright", "panel_01_center"
		};

		String[] panel_02_Textures = new String[]
		{
			"panel_02_upleft", "panel_02_downright", "panel_02_center"
		};

		TEXTURE_MAP.put(STYLE_PANEL_01, panel_01_Textures);
		TEXTURE_MAP.put(STYLE_PANEL_02, panel_02_Textures);

		Vector3f[] green_colors = new Vector3f[]
		{
			Color.toVector(Integer.parseInt("a6fcdb", 16), new Vector3f()),
			Color.toVector(Integer.parseInt("249fde", 16), new Vector3f()),
			Color.toVector(Integer.parseInt("20d6c7", 16), new Vector3f()),
		};

		IMAGE_COLOR_MAP.put(COLOR_GREEN, green_colors);


		Vector3f[] green_up = new Vector3f[]
		{
			Color.toVector(Integer.parseInt("a6fcdb", 16), new Vector3f()),
			Color.toVector(Integer.parseInt("249fde", 16), new Vector3f()),
			Color.toVector(Integer.parseInt("20d6c7", 16), new Vector3f()),
		};

		Vector3f[] green_hover = new Vector3f[]
		{
			Color.toVector(Integer.parseInt("a3ffdb", 16), new Vector3f()),
			Color.toVector(Integer.parseInt("09a7f9", 16), new Vector3f()),
			Color.toVector(Integer.parseInt("05f0dc", 16), new Vector3f()),
		};

		Vector3f[] green_down = new Vector3f[]
		{
			Color.toVector(Integer.parseInt("09a7f9", 16), new Vector3f()),
			Color.toVector(Integer.parseInt("a3ffdb", 16), new Vector3f()),
			Color.toVector(Integer.parseInt("05f0dc", 16), new Vector3f()),
		};
		ComposedButtonColorMap button_green_colors = new ComposedButtonColorMap(green_up, green_hover, green_down);

		BUTTON_COLOR_MAP.put(COLOR_GREEN, button_green_colors);
	}

	public static String[] texturesFromStyle(String style)
	{
		return TEXTURE_MAP.get(style);
	}

	public static Vector3f[] imageColorsFromScheme(String scheme)
	{
		return IMAGE_COLOR_MAP.get(scheme);
	}

	public static ComposedButtonColorMap buttonColorsFromScheme(String scheme)
	{
		return BUTTON_COLOR_MAP.get(scheme);
	}
}

package zyx.engine.components.screen.composed;

import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.Color;
import zyx.utils.cheats.Print;

public class ComposedConstants
{

	private static final String STYLE_PANEL_01 = "panel_01";
	private static final String STYLE_PANEL_02 = "panel_02";

	private static final String COLOR_GREEN = "green";
	private static final String COLOR_GREEN_INV = "greenInv";
	
	private static final String COLOR_GRAY = "gray";
	
	private static final HashMap<String, String[]> TEXTURE_MAP = new HashMap<>();
	private static final HashMap<String, Vector3f[]> IMAGE_COLOR_MAP = new HashMap<>();
	private static final HashMap<String, ComposedButtonColorMap> BUTTON_COLOR_MAP = new HashMap<>();

	static
	{
		addTextureGroup(STYLE_PANEL_01, "panel_01");
		addTextureGroup(STYLE_PANEL_02, "panel_02");

		Vector3f greenCenter = getColor("20D6C7");
		Vector3f greenOutUpper = getColor("A6FCDB");
		Vector3f greenOutLower = getColor("249FDE");

		Vector3f greenInUpper = getColor("17C0B2");
		Vector3f greenInLower = getColor("24E4D4");

		Vector3f greenHighlight = getColor("05F0DC");
		Vector3f greenHighlightUpper = getColor("A3FFDB");
		Vector3f greenHighlightLower = getColor("09A7F9");

		Vector3f grayCenter = getColor("7B7B7B");
		Vector3f grayOutUpper = getColor("D1D1D1");
		Vector3f grayOutLower = getColor("818181");

		Vector3f grayHighlight = getColor("7A7A7A");
		Vector3f grayHighlightUpper = getColor("D0D0D0");
		Vector3f grayHighlightLower = getColor("808080");

		addImageColor(COLOR_GREEN, greenOutUpper, greenOutLower, greenCenter);
		addImageColor(COLOR_GREEN_INV, greenInUpper, greenInLower, greenCenter);

		addButtonColor(COLOR_GREEN, greenOutUpper, greenOutLower, greenCenter, greenHighlightUpper, greenHighlightLower, greenHighlight);
		addButtonColor(COLOR_GRAY, grayOutUpper, grayOutLower, grayCenter, grayHighlightUpper, grayHighlightLower, grayHighlight);
	}

	public static String[] texturesFromStyle(String style)
	{
		if (TEXTURE_MAP.containsKey(style))
		{
			return TEXTURE_MAP.get(style);
		}
		else
		{
			String msg = String.format("[Warning]: No texture style named '%s' was found! Using default instead..", style);
			Print.out(msg);
			
			return TEXTURE_MAP.get(STYLE_PANEL_01);
		}
	}

	public static Vector3f[] imageColorsFromScheme(String scheme)
	{
		if (IMAGE_COLOR_MAP.containsKey(scheme))
		{
			return IMAGE_COLOR_MAP.get(scheme);
		}
		else
		{
			String msg = String.format("[Warning]: No color scheme named '%s' was found for images! Using default instead..", scheme);
			Print.out(msg);
			
			return IMAGE_COLOR_MAP.get(COLOR_GREEN);
		}
	}

	public static ComposedButtonColorMap buttonColorsFromScheme(String scheme)
	{
		if (BUTTON_COLOR_MAP.containsKey(scheme))
		{
			return BUTTON_COLOR_MAP.get(scheme);
		}
		else
		{
			String msg = String.format("[Warning]: No color scheme named '%s' was found for buttons! Using default instead..", scheme);
			Print.out(msg);
			
			return BUTTON_COLOR_MAP.get(COLOR_GREEN);
		}
	}

	private static Vector3f getColor(String hexCode)
	{
		Vector3f output = new Vector3f();
		Color.toVector(Integer.parseInt(hexCode, 16), output);

		return output;
	}

	private static void addButtonColor(String color, Vector3f upper, Vector3f lower, Vector3f center, Vector3f upHigh, Vector3f lowerHigh, Vector3f centerHigh)
	{
		Vector3f[] upColors = new Vector3f[]
		{
			upper, lower, center,
		};

		Vector3f[] hoverColors = new Vector3f[]
		{
			upHigh, lowerHigh, centerHigh,
		};

		Vector3f[] downColors = new Vector3f[]
		{
			lowerHigh, upHigh, centerHigh,
		};

		ComposedButtonColorMap composedColorMap = new ComposedButtonColorMap(upColors, hoverColors, downColors);
		BUTTON_COLOR_MAP.put(color, composedColorMap);
	}

	private static void addImageColor(String color, Vector3f upper, Vector3f lower, Vector3f center)
	{
		Vector3f[] colorArray = new Vector3f[]
		{
			upper, lower, center,
		};

		IMAGE_COLOR_MAP.put(color, colorArray);
	}

	private static void addTextureGroup(String style, String prefix)
	{
		String[] textureArray = new String[]
		{
			prefix + "_upleft",
			prefix + "_downright",
			prefix + "_center"
		};

		TEXTURE_MAP.put(style, textureArray);
	}
}

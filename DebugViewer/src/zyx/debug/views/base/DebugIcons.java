package zyx.debug.views.base;

import java.io.File;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class DebugIcons
{
	private static final String NETWORK_TEMPLATE = "assets/debug/network/%s.png";
	private static final String TEMPLATE = "assets/debug/%s";
	
	private static HashMap<String, ImageIcon> ICON_MAP = new HashMap<>();
	private static HashMap<Class, ImageIcon> CLASS_MAP = new HashMap<>();

	public static Icon createIcon(String folderPath)
	{
		ImageIcon icon = ICON_MAP.get(folderPath);
		
		if (icon == null)
		{
			String path = String.format(TEMPLATE, folderPath);
			File file = new File(path);
			
			icon = new ImageIcon(file.getAbsolutePath());
			
			ICON_MAP.put(folderPath, icon);
		}

		return icon;
	}

	public static Icon createIcon(Class clazz)
	{
		ImageIcon icon = CLASS_MAP.get(clazz);

		if (icon == null)
		{
			String className = clazz.getSimpleName();
			className = className.replace("[]", "Array");
			
			String path = String.format(NETWORK_TEMPLATE, className);
			File file = new File(path);

			icon = new ImageIcon(file.getAbsolutePath());

			CLASS_MAP.put(clazz, icon);
		}

		return icon;
	}
}

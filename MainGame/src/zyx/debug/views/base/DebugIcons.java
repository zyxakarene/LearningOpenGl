package zyx.debug.views.base;

import java.io.File;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class DebugIcons
{
	private static final String TEMPLATE = "assets/debug/%s/%s";
	
	private static HashMap<Class, ImageIcon> ICON_MAP = new HashMap<>();

	public static Icon createIcon(IDebugIcon value)
	{
		Class clazz = value.getClass();
		ImageIcon icon = ICON_MAP.get(clazz);
		
		if (icon == null)
		{
			String path = String.format(TEMPLATE, value.getDebugIconFolder(), value.getDebugIcon());
			File file = new File(path);
			
			icon = new ImageIcon(file.getAbsolutePath());
			
			ICON_MAP.put(clazz, icon);
		}

		return icon;
	}
}

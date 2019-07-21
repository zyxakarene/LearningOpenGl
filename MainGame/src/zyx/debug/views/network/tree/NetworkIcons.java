package zyx.debug.views.network.tree;

import java.io.File;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;

class NetworkIcons
{
	private static final String PATH_TEMPLATE = "assets/debug/network/%s.png";
	
	private static HashMap<Class, ImageIcon> ICON_MAP = new HashMap<>();

	static Icon createIcon(Class clazz)
	{
		ImageIcon icon = ICON_MAP.get(clazz);

		if (icon == null)
		{
			String className = clazz.getSimpleName();
			className = className.replace("[]", "Array");
			
			String path = String.format(PATH_TEMPLATE, className);
			File file = new File(path);

			icon = new ImageIcon(file.getAbsolutePath());

			ICON_MAP.put(clazz, icon);
		}

		return icon;
	}

}

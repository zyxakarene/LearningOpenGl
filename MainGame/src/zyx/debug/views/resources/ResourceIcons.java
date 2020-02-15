package zyx.debug.views.resources;

import java.io.File;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import zyx.engine.resources.impl.Resource;

class ResourceIcons
{
	private static final String FOLDER = "assets/debug/resource/";
	
	private static HashMap<Class, ImageIcon> ICON_MAP = new HashMap<>();

	static Icon createIcon(Resource value)
	{
		Class clazz = value.getClass();
		ImageIcon icon = ICON_MAP.get(clazz);
		
		if (icon == null)
		{
			String path = FOLDER + value.getResourceIcon();
			File file = new File(path);
			
			icon = new ImageIcon(file.getAbsolutePath());
			
			ICON_MAP.put(clazz, icon);
		}

		return icon;
	}
}

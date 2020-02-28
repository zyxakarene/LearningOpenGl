package zyx.debug.views.base;

import java.io.File;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class DebugIcons
{
	private static final String TEMPLATE = "assets/debug/%s";
	
	private static HashMap<String, ImageIcon> ICON_MAP = new HashMap<>();

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
}

package zyx.gui.tree;

import java.io.File;
import java.io.FileFilter;

public class JsonFilter implements FileFilter
{

	@Override
	public boolean accept(File file)
	{
		return file.isDirectory() || file.getName().endsWith(".json");
	}

}

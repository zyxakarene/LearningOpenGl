package zyx.debug.views.resources.watcher;

import java.io.File;
import java.io.FileFilter;

public class FolderFilter implements FileFilter
{

	@Override
	public boolean accept(File pathname)
	{
		return pathname.exists() && pathname.isDirectory();
	}

}

package zyx.debug.views.resources.watcher;

import java.io.File;

public interface IFileUpdated
{
	public String[] getPaths();
	public void filesUpdated(File[] files);
}

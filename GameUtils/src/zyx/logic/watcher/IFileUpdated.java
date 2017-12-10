package zyx.logic.watcher;

import java.io.File;

public interface IFileUpdated
{

	public String getFormat();
	public String getPath();
	public String getOutputCopyPath();

	public void fileUpdated(File file);
}

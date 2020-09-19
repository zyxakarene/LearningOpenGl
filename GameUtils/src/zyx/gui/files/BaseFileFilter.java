package zyx.gui.files;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class BaseFileFilter extends FileFilter
{

	private final String format;
	private final String description;

	public BaseFileFilter(String format)
	{
		this.format = "." + format;
		this.description = format.toUpperCase() + " files";
	}

	@Override
	public boolean accept(File f)
	{
		return f.isDirectory() || f.getName().endsWith(format);
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	
	
}

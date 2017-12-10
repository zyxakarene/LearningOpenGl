package zyx.logic.converter.smd.control;

import java.io.File;
import java.io.FileFilter;

public class QcFilter implements FileFilter
{

	@Override
	public boolean accept(File file)
	{
		return file.isDirectory() || file.getName().endsWith(".qc");
	}

}

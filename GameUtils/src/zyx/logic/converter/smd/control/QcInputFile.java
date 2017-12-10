package zyx.logic.converter.smd.control;

import java.io.File;
import zyx.UtilConstants;

public class QcInputFile extends File
{

	public QcInputFile(String pathname)
	{
		super(pathname);
	}

	@Override
	public String toString()
	{
		String path = UtilConstants.MESH_FOLDER;
		return getPath().substring(path.length());
	}
	
	
}

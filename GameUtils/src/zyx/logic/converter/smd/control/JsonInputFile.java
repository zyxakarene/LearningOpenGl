package zyx.logic.converter.smd.control;

import java.io.File;
import zyx.UtilConstants;

public class JsonInputFile extends File
{

	public JsonInputFile(String pathname)
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

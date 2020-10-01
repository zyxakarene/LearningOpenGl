package zyx.logic.converter.smd.control;

import java.io.File;
import java.util.ArrayList;
import zyx.UtilConstants;

public class JsonScanner
{
	private static final JsonFilter FILTER = new JsonFilter();
	
	public static ArrayList<File> getJsonFiles()
	{
		ArrayList<File> files = new ArrayList<>();
		File root = new File(UtilConstants.MESH_FOLDER);
		scanFile(root, files);
		
		return files;
	}

	private static void scanFile(File file, ArrayList<File> out)
	{
		if (file.isDirectory())
		{
			File[] children = file.listFiles(FILTER);
			for (File child : children)
			{
				scanFile(new JsonInputFile(child.getAbsolutePath()), out);
			}
		}
		else
		{
			out.add(file);
		}
	}
}

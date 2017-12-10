package zyx.logic.converter.smd.control;

import java.io.File;
import java.util.ArrayList;
import zyx.UtilConstants;

public class QcScanner
{
	private static final QcFilter FILTER = new QcFilter();
	
	public static ArrayList<File> getQcFiles()
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
				scanFile(new QcInputFile(child.getAbsolutePath()), out);
			}
		}
		else
		{
			out.add(file);
		}
	}
}

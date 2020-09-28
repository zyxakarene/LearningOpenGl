package zyx.gui.tree;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import zyx.UtilConstants;
import zyx.logic.converter.smd.control.JsonFilter;

class TreeFileScanner
{

	private static JsonFilter FILTER = new JsonFilter();
	private static FileComparator COMPARATOR = new FileComparator();

	static JsonTreeNode getRoot()
	{
		File baseFolder = new File(UtilConstants.MESH_FOLDER);
		JsonTreeNode root = new JsonTreeNode(baseFolder);
		
		File[] children = baseFolder.listFiles(FILTER);
		Arrays.sort(children, COMPARATOR);
		for (File child : children)
		{
			scan(child, root);
		}
		
		return root;
	}
	
	private static void scan(File file, JsonTreeNode node)
	{
		if (file.isDirectory())
		{
			JsonTreeNode folderNode = new JsonTreeNode(file);
			node.add(folderNode);
			
			File[] children = file.listFiles(FILTER);
			Arrays.sort(children, COMPARATOR);
			for (File child : children)
			{
				scan(child, folderNode);
			}
		}
		else
		{
			JsonTreeNode folderNode = new JsonTreeNode(file);
			node.add(folderNode);
		}
	}
	
	private static class FileComparator implements Comparator<File>
	{

		@Override
		public int compare(File a, File b)
		{
			boolean aFolder = a.isDirectory();
			boolean bFolder = b.isDirectory();
			
			if (aFolder != bFolder)
			{
				if (bFolder)
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}
			
			String aName = a.getName();
			String bName = b.getName();
			
			return aName.compareTo(bName);
		}
	}

}

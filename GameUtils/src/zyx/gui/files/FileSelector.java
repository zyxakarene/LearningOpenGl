package zyx.gui.files;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import zyx.UtilConstants;

public class FileSelector
{

	public static File openFile(Component parent, FileSelectorType view)
	{
		return openInner(parent, UtilConstants.BASE_FOLDER, view, true);
	}
	
	public static File saveFile(Component parent, FileSelectorType view)
	{
		return openInner(parent, UtilConstants.ASSETS_OUTPUT, view, false);
	}

	public static File openFile(Component parent, FileSelectorType view, String startFolder)
	{
		return openInner(parent, startFolder, view, true);
	}
	
	public static File saveFile(Component parent, FileSelectorType view, String startFolder)
	{
		return openInner(parent, startFolder, view, false);
	}

	private static File openInner(Component parent, String folder, FileSelectorType view, boolean isOpen)
	{
		File startFolder = new File(folder);
		if (startFolder.isFile() || !startFolder.exists())
		{
			startFolder = startFolder.getParentFile();
		}
		
		JFileChooser chooser = new JFileChooser(startFolder);
		chooser.setFileFilter(view.filter);
		
		int result;
		if (isOpen)
		{
			result = chooser.showOpenDialog(parent);	
		}
		else
		{
			result = chooser.showSaveDialog(parent);
		}
		
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selection = chooser.getSelectedFile();
			if (selection.getName().endsWith(view.format) == false)
			{
				return new File(selection.getAbsolutePath() + "." + view.format);
			}
			return chooser.getSelectedFile();
		}
		
		return null;
	}

}

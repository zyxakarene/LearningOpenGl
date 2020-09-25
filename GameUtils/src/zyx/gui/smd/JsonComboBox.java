package zyx.gui.smd;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JComboBox;
import zyx.logic.converter.smd.control.JsonScanner;

public class JsonComboBox extends JComboBox<File>
{

	public JsonComboBox()
	{
		addItem(new File("=ALL="));
		
		ArrayList<File> jsonFiles = JsonScanner.getJsonFiles();
		for (File jsonFile : jsonFiles)
		{
			addItem(jsonFile);
		}
	}

	@Override
	public File getSelectedItem()
	{
		return (File) dataModel.getSelectedItem();
	}

}

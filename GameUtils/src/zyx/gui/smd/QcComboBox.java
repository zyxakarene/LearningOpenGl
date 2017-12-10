package zyx.gui.smd;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JComboBox;
import zyx.logic.converter.smd.control.QcScanner;

public class QcComboBox extends JComboBox<File>
{

	public QcComboBox()
	{
		ArrayList<File> qcFiles = QcScanner.getQcFiles();
		for (File qcFile : qcFiles)
		{
			addItem(qcFile);
		}
	}

	@Override
	public File getSelectedItem()
	{
		return (File) dataModel.getSelectedItem();
	}

}

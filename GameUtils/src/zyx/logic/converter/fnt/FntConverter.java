package zyx.logic.converter.fnt;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import zyx.UtilConstants;
import zyx.logic.DragDropper;
import zyx.logic.DragDropper.IFilesDropped;
import zyx.logic.converter.InputToOutput;
import zyx.logic.watcher.IFileUpdated;

public class FntConverter implements IFilesDropped, IFileUpdated
{

	private static final String INPUT_FORMAT = "fnt";
	private static final String OUTPUT_FORMAT = "zff"; //Zyx Font Format

	private final DragDropper dropper;
	private final JPanel fntPanel;

	public FntConverter(JPanel fntPanel)
	{
		this.fntPanel = fntPanel;

		dropper = new DragDropper(INPUT_FORMAT, this);
	}

	@Override
	public void filesDropped(List<File> files)
	{
		for (File file : files)
		{
			handleFile(file);
		}
	}
	
	@Override
	public String getPath()
	{
		return UtilConstants.FONT_FOLDER;
	}
	
	@Override
	public String getOutputCopyPath()
	{
		return UtilConstants.FONT_OUTPUT;
	}
	
	@Override
	public String getFormat()
	{
		return INPUT_FORMAT;
	}
	
	@Override
	public void fileUpdated(File file)
	{
		handleFile(file);
	}

	@Override
	public JPanel getDropPanel()
	{
		return fntPanel;
	}

	private void handleFile(File file)
	{
		try
		{			
			File output = InputToOutput.getOutputFrom(file, INPUT_FORMAT, OUTPUT_FORMAT, getOutputCopyPath());

			XmlFileReader reader = new XmlFileReader(file);
			reader.convertTo(output);
		}
		catch (ParserConfigurationException | SAXException | IOException ex)
		{
			Logger.getLogger(FntConverter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}

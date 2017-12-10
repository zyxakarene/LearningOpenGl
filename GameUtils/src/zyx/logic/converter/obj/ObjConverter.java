package zyx.logic.converter.obj;

import java.io.*;
import java.util.List;
import javax.swing.JPanel;
import zyx.UtilConstants;
import zyx.logic.DragDropper;
import zyx.logic.DragDropper.IFilesDropped;
import zyx.logic.UtilsLogger;
import zyx.logic.converter.InputToOutput;
import zyx.logic.watcher.IFileUpdated;

public class ObjConverter implements IFilesDropped, IFileUpdated
{

	private static final String INPUT_FORMAT = "obj";
	private static final String OUTPUT_FORMAT = "zmf"; //Zyx Model Format
	
	private final DragDropper dropper;
	private final JPanel dropPanel;

	public ObjConverter(JPanel dropPanel)
	{
		this.dropPanel = dropPanel;

		dropper = new DragDropper(INPUT_FORMAT, this);
	}
	
	@Override
	public String getFormat()
	{
		return INPUT_FORMAT;
	}

	@Override
	public String getPath()
	{
		return UtilConstants.MESH_FOLDER;
	}
	
	@Override
	public String getOutputCopyPath()
	{
		return UtilConstants.MESH_OUTPUT;
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
	public void fileUpdated(File file)
	{
		handleFile(file);
	}

	@Override
	public JPanel getDropPanel()
	{
		return dropPanel;
	}

	private void handleFile(File file)
	{
		try
		{
			ExternalModelManager manager = new ExternalModelManager();
			manager.load(file);
			
			File output = InputToOutput.getOutputFrom(file, INPUT_FORMAT, OUTPUT_FORMAT, getOutputCopyPath());
			
			saveNewFile(output, manager.vertexData, manager.elementData);
			
			UtilsLogger.log("Saved output file" + output.getName());
		}
		catch (IOException ex)
		{
			UtilsLogger.log("Could not convert model", ex);
		}
	}

	private void saveNewFile(File output, float[] vertexData, int[] elementData) throws FileNotFoundException, IOException
	{
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output)))
		{
			out.writeInt(vertexData.length);
			out.writeInt(elementData.length);
			
			int vertexLen = vertexData.length;
			for (int i = 0; i < vertexLen; i++)
			{
				out.writeFloat(vertexData[i]);
			}
			
			int elementLen = elementData.length;
			for (int i = 0; i < elementLen; i++)
			{
				out.writeShort(elementData[i]);
			}
			
			out.flush();
		}
		
	}

}

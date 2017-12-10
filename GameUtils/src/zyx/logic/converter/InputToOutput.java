package zyx.logic.converter;

import java.io.File;

public class InputToOutput
{

	public static File getOutputFrom(File input, String inputFormat, String outputFormat, String outputPath)
	{
		String newName = input.getName().replace(inputFormat, outputFormat);
		
		return new File(outputPath + File.separator + newName);
	}
}

package zyx.logic.converter.smd;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import zyx.logic.converter.smd.parsedVo.ParsedSmdFile;
import zyx.logic.converter.smd.reader.SmdLineReader;

public class SmdFileParser
{
	public static ParsedSmdFile parseFile(File file)
	{
		ParsedSmdFile result = new ParsedSmdFile();
		SmdLineReader linereader = new SmdLineReader(result);
		
		try (Scanner scan = new Scanner(file))
		{
			linereader.read(scan);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}

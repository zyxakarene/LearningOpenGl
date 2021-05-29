package zyx.logic.converter.smdV2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;
import zyx.logic.converter.smdV2.reader.SmdLineReader;

public class SmdFileParser
{
	private SmdLineReader linereader;
	
	private final ParsedSmdFile result;
	private final File file;

	public SmdFileParser(File file)
	{
		this.file = file;
		
		result = new ParsedSmdFile();
		linereader = new SmdLineReader(result);
		
		parseFile();
	}
	
	private void parseFile()
	{
		try (Scanner scan = new Scanner(file))
		{
			linereader.read(scan);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public ParsedSmdFile getResult()
	{
		return result;
	}
}

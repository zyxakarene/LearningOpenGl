package zyx.logic.converter.smdV2.reader;

import java.util.Scanner;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;

public class SmdLineReader
{
	static final String READER_TYPE_NODES = "nodes";
	static final String READER_TYPE_SKELETON = "skeleton";
	static final String READER_TYPE_TRIANGLES = "triangles";
		
	private final SmdLineReaderNode nodeReader;
	private final SmdLineReaderSkeleton skeletonReader;
	private final SmdLineReaderTriangle triangleReader;

	public SmdLineReader(ParsedSmdFile output)
	{
		nodeReader = new SmdLineReaderNode(output.bones);
		skeletonReader = new SmdLineReaderSkeleton(output.frames);
		triangleReader = new SmdLineReaderTriangle(output.surfaces);
	}

	public void read(Scanner scan)
	{
		while (scan.hasNextLine())
		{			
			String line = scan.nextLine();
			
			if (line.startsWith(READER_TYPE_NODES))
			{
				nodeReader.readFrom(scan);
			}
			else if (line.startsWith(READER_TYPE_SKELETON))
			{
				skeletonReader.readFrom(scan);
			}
			else if (line.startsWith(READER_TYPE_TRIANGLES))
			{
				triangleReader.readFrom(scan);
			}
		}
	}

}

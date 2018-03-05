package zyx.logic.converter.smd.control;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class QcParser
{
	private static final String MESH_START = "$mesh";
	private static final String PHYS_START = "$phys";
	private static final String TEXTURE_START = "$texture";
	private static final String ANIMATION_START = "$animation";
	private static final String OUT_MODEL_START = "$out_model";
	private static final String OUT_TEXTURE_START = "$out_texture";
	
	private QcFile qc;
	private QcLineReader reader;

	public QcParser()
	{
		qc = new QcFile();
		reader = new QcLineReader();
	}
	
	public QcFile parseFile(File input) throws IOException
	{
		reader.setRoot(input.getParentFile());
		
		try (Scanner scan = new Scanner(input))
		{
			while (scan.hasNextLine())
			{			
				String line = scan.nextLine();
				handleLine(line);
			}
			
			return qc;
		}
	}

	private void handleLine(String line)
	{
		if (line.startsWith(MESH_START))
		{
			reader.readMesh(line, qc);
		}
		else if (line.startsWith(PHYS_START))
		{
			reader.readPhys(line, qc);
		}
		else if (line.startsWith(TEXTURE_START))
		{
			reader.readTexture(line, qc);
		}
		else if (line.startsWith(ANIMATION_START))
		{
			reader.readAnimation(line, qc);
		}
		else if (line.startsWith(OUT_MODEL_START))
		{
			reader.readOutModel(line, qc);
		}
		else if (line.startsWith(OUT_TEXTURE_START))
		{
			reader.readOutTexture(line, qc);
		}
	}
}

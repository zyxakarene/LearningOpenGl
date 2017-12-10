package zyx.dev;

import java.io.*;
import zyx.dev.vo.SmdObject;
import java.util.List;
import zyx.dev.reader.SmdImporter;

public class SmdParser
{

	private File ref;
	private File[] animations;

	private SmdObject result;

	public SmdParser(File pRef, List<File> pAnimations)
	{
		ref = pRef;
		animations = new File[pAnimations.size()];
		pAnimations.toArray(animations);
		result = new SmdObject();
	}

	public void parseFiles() throws IOException
	{
		SmdImporter importer = new SmdImporter();
		importer.importModel(ref);
		importer.importAnimations(animations);
		
		SmdObject smd = importer.getSmd();
		
		File output = new File("knight.zaf");
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output)))
		{
			smd.save(out);
			out.flush();
		}
	}

}

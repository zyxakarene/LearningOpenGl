package zyx.logic.converter.smd;

import java.io.*;
import zyx.logic.converter.smd.vo.SmdObject;
import java.util.List;
import zyx.logic.converter.smd.control.QcFile;
import zyx.logic.converter.smd.reader.SmdImporter;

public class SmdParser
{

	private File ref;
	private File[] animations;

	private File output;
	private final QcFile qc;

	public SmdParser(QcFile parsedQc)
	{
		this.qc = parsedQc;
		
		ref = parsedQc.meshFile;
		output = parsedQc.outModel;
		
		animations = new File[parsedQc.animations.size()];
		parsedQc.animations.toArray(animations);
	}

	public void parseFiles() throws IOException
	{
		SmdImporter importer = new SmdImporter();
		importer.importModel(ref);
		importer.importAnimations(animations);
		
		SmdObject smd = importer.getSmd();
		smd.setTexturePath(qc.textureFile.getName());
		
		if (output.exists() == false)
		{
			output.getParentFile().mkdirs();
		}
		
		DataOutputStream out = new DataOutputStream(new FileOutputStream(output));
		
		smd.save(out);
		out.flush();
		out.close();
	}

}

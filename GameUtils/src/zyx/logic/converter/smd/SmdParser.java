package zyx.logic.converter.smd;

import java.io.*;
import zyx.logic.converter.smd.vo.SmdObject;
import zyx.logic.converter.smd.control.QcFile;
import zyx.logic.converter.smd.reader.SmdImporter;

public class SmdParser
{

	private File ref;
	private File phys;
	private File bounding;
	private File[] animations;

	private File output;
	private final QcFile qc;

	public SmdParser(QcFile parsedQc)
	{
		this.qc = parsedQc;
		
		ref = parsedQc.meshFile;
		phys = parsedQc.physFile;
		bounding = parsedQc.boundingFile;
		output = parsedQc.outModel;
		
		animations = new File[parsedQc.animations.size()];
		parsedQc.animations.toArray(animations);
	}

	public void parseFiles() throws IOException
	{
		SmdImporter importer = new SmdImporter();
		importer.importModel(ref);
		importer.importPhys(phys);
		importer.importBounding(bounding);
		importer.importAnimations(animations);
		
		SmdObject smd = importer.getSmd();
		smd.setTexturePath(qc.getTextureName());
		
		if (output.exists() == false)
		{
			output.getParentFile().mkdirs();
		}
		
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output)))
		{
			smd.save(out);
			out.flush();
		}
	}

}

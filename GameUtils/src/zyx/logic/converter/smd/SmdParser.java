package zyx.logic.converter.smd;

import java.io.*;
import zyx.logic.converter.smd.control.QcAnimation;
import zyx.logic.converter.smd.vo.SmdObject;
import zyx.logic.converter.smd.control.QcFile;
import zyx.logic.converter.smd.reader.SmdImporter;

public class SmdParser
{

	private boolean isSkeleton;
	
	private File ref;
	private File phys;
	private File bounding;
	private QcAnimation[] animations;

	private File outputModel;
	private final QcFile qc;

	public SmdParser(QcFile parsedQc)
	{
		this.qc = parsedQc;
		
		isSkeleton = parsedQc.isSkeleton;
		
		ref = parsedQc.meshFile;
		phys = parsedQc.physFile;
		bounding = parsedQc.boundingFile;
		outputModel = parsedQc.outModel;
		
		animations = new QcAnimation[parsedQc.animations.size()];
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
		smd.setSkeleton(qc.isSkeleton);
		smd.setSkeletonPath(qc.skeletonResource);
		smd.setDiffuseTexturePath(qc.getDiffuseTextureName());
		smd.setNormalTexturePath(qc.getNormalTextureName());
		smd.setSpecularTexturePath(qc.getSpecularTextureName());
		
		if (outputModel.exists() == false)
		{
			outputModel.getParentFile().mkdirs();
		}
		
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(outputModel)))
		{
			smd.save(out);
			out.flush();
		}
	}

}

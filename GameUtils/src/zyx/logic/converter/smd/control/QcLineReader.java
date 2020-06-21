package zyx.logic.converter.smd.control;

import java.io.File;
import zyx.UtilConstants;

class QcLineReader
{
	private static final String REGEX = "\\s+";
	private static final String TYPE_SKELETON = "skeleton";
	private static final String TYPE_MESH = "mesh";
	private static final String LOOP = "loop";
	
	private File root;

	QcLineReader()
	{
	}
	
	void setRoot(File root)
	{
		this.root = root;
	}
	
	void readMesh(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.meshFile = new File(root.getAbsolutePath() + File.separator + split[1]);
	}
	
	void readSkeleton(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.skeletonResource = split[1];
	}
	
	void readType(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.isSkeleton = TYPE_SKELETON.equals(split[1]);
	}
	
	void readPhys(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.physFile = new File(root.getAbsolutePath() + File.separator + split[1]);
	}

	void readTexture(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.diffuseTextureResource = split[1];
	}

	void readNormal(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.normalTextureResource = split[1];
	}

	void readSpecular(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.specularTextureResource = split[1];
	}

	void readBoundingBox(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.boundingFile = new File(root.getAbsolutePath() + File.separator +  split[1]);
	}

	void readAnimation(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		String name = split[1];
		File animationFile = new File(root.getAbsolutePath() + File.separator +  split[2]);
		boolean looping = split.length > 3 && LOOP.equals(split[3]);
		
		QcAnimation animation = new QcAnimation(animationFile, name, looping);
		
		qc.animations.add(animation);
	}
	
	void readOutModel(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		String path = split[1];
		path = path.replace("$assets$", UtilConstants.ASSETS_OUTPUT);
		
		qc.outModel = new File(path);
	}
}

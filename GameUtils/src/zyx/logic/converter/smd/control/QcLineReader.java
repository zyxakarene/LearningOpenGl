package zyx.logic.converter.smd.control;

import java.io.File;
import zyx.UtilConstants;

class QcLineReader
{
	private static final String REGEX = "\\s+";
	
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
		File animation = new File(root.getAbsolutePath() + File.separator +  split[2]);
		
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

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

	void readTexture(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		qc.textureFile = new File(root.getAbsolutePath() + File.separator +  split[1]);
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

	void readOutTexture(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		String path = split[1];
		path = path.replace("$assets$", UtilConstants.ASSETS_OUTPUT);
		
		qc.outTexture = new File(path);
	}
}

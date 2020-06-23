package zyx.logic.converter.smd.control;

import java.io.File;
import zyx.UtilConstants;
import zyx.logic.UtilsLogger;

class QcLineReader
{

	private static final String REGEX = "\\s+";
	private static final String POST_DATA_SEPERATOR = ";";
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
		qc.boundingFile = new File(root.getAbsolutePath() + File.separator + split[1]);
	}

	void readAnimation(String line, QcFile qc)
	{
		String[] split = line.split(REGEX);
		String name = split[1];
		File animationFile = new File(root.getAbsolutePath() + File.separator + split[2]);

		int start = 0;
		int end = Short.MAX_VALUE;
		short blendTime = 0;
		boolean looping = false;

		if (split.length >= 4)
		{
			String postData = split[3];
			String[] postSplit = postData.split(POST_DATA_SEPERATOR);
			
			for (String data : postSplit)
			{
				System.out.println(data);
				String[] keyValue = data.split(":");
				String key = keyValue[0];
				String value = keyValue[1];
				
				if (key.equalsIgnoreCase("frames"))
				{
					String[] frameSplit = value.split("-");
					start = Integer.parseInt(frameSplit[0]);
					end = Integer.parseInt(frameSplit[1]);
				}
				else if (key.equalsIgnoreCase("loop"))
				{
					looping = Boolean.parseBoolean(value);
				}
				else if (key.equalsIgnoreCase("blend"))
				{
					blendTime = Short.parseShort(value);
				}
				else
				{
					UtilsLogger.log("Unknown postData type: " + data);
				}
			}
		}

		QcAnimation animation = new QcAnimation(animationFile, name, start, end, blendTime, looping);

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

package zyx.logic.converter.smd.reader;

import zyx.logic.UtilsLogger;

public class SmdBoneCheckHandler implements ISmdHandler
{

	private byte maxBoneCount;

	public SmdBoneCheckHandler()
	{
		maxBoneCount = 1;
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		if (split.length < 9)
		{
			return;
		}

		byte bonesInLine = 1;
		if (split.length > 9)
		{
			bonesInLine = Byte.parseByte(split[9]);
		}

		if (bonesInLine > maxBoneCount)
		{
			maxBoneCount = bonesInLine;
			UtilsLogger.log("MaxBoneCount now at: " + maxBoneCount);
		}
	}

	@Override
	public Byte getResult()
	{
		return maxBoneCount;
	}

	@Override
	public void setData(Object data)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}

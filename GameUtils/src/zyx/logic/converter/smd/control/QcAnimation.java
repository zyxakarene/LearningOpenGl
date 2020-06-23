package zyx.logic.converter.smd.control;

import java.io.File;

public class QcAnimation
{
	public File file;
	public String name;
	public boolean looping;
	public int start;
	public int end;
	public short blendTime;

	public QcAnimation(File file, String name, int start, int end, short blendTime, boolean looping)
	{
		this.file = file;
		this.name = name;
		this.start = start;
		this.end = end;
		this.blendTime = blendTime;
		this.looping = looping;
	}
}

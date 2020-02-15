package zyx.logic.converter.smd.control;

import java.io.File;

public class QcAnimation
{
	public File file;
	public String name;
	public boolean looping;

	public QcAnimation(File file, String name, boolean looping)
	{
		this.file = file;
		this.name = name;
		this.looping = looping;
	}
}

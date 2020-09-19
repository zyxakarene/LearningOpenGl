package zyx.gui.files;

import javax.swing.filechooser.FileFilter;


public enum FileSelectorType
{
	ZAF("zaf"),
	SKELETON("skeleton"),
	SMD("smd"),
	JSON("json"),
	PNG("png");
	
	final FileFilter filter;
	final String format;

	private FileSelectorType(String format)
	{
		this.format = format;
		this.filter = new BaseFileFilter(format);
	}
	
}

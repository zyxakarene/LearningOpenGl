package zyx.logic;

import java.awt.Component;
import java.io.File;
import java.util.List;


public interface IFilesDropped
{

	public void filesDropped(List<File> files);

	public Component getDropPanel();
	
}

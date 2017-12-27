package zyx.logic.converter.smd.control;

import java.io.File;
import java.util.ArrayList;

public class QcFile
{
	public File meshFile;
	public File textureFile;
	
	public ArrayList<File> animations = new ArrayList<>();
	
	public File outModel;
	public File outTexture;

	@Override
	public String toString()
	{
		return "QcFile{" + "meshFile=" + meshFile + ", textureFile=" + textureFile + ", animations=" + animations + ", outModel=" + outModel + ", outTexture=" + outTexture + '}';
	}
	
	public String getTextureName()
	{
		if (textureFile != null)
		{
			return textureFile.getName();
		}
		
		return "";
	}
	
	
}

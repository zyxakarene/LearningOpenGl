package zyx.logic.converter.smd.control;

import java.io.File;
import java.util.ArrayList;

public class QcFile
{

	public File meshFile;
	public File physFile;
	public File boundingFile;
	public String diffuseTextureResource;
	public String normalTextureResource;
	public String specularTextureResource;

	public ArrayList<File> animations = new ArrayList<>();

	public File outModel;

	@Override
	public String toString()
	{
		return "QcFile{" + "meshFile=" + meshFile + ", physFile=" + physFile + ", boundingFile=" + boundingFile + ", diffuseTextureResource=" + diffuseTextureResource + 
				", normalTextureResource=" + normalTextureResource + ", specularTextureResource=" + specularTextureResource + ", animations=" + animations + 
				", outModel=" + outModel + "}";
	}

	public String getDiffuseTextureName()
	{
		if (diffuseTextureResource != null)
		{
			return diffuseTextureResource;
		}

		return "texture.default_diffuse";
	}

	public String getNormalTextureName()
	{
		if (normalTextureResource != null)
		{
			return normalTextureResource;
		}

		return "normal.default_normal";
	}

	public String getSpecularTextureName()
	{
		if (specularTextureResource != null)
		{
			return specularTextureResource;
		}

		return "specular.default_specular";
	}

}

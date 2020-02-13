package zyx.logic.converter.smd.control;

import java.io.File;
import java.util.ArrayList;

public class QcFile
{

	public boolean isSkeleton;

	public File meshFile;
	public File physFile;
	public File boundingFile;
	public String skeletonResource;
	public String diffuseTextureResource;
	public String normalTextureResource;
	public String specularTextureResource;

	public ArrayList<QcAnimation> animations = new ArrayList<>();

	public File outModel;

	@Override
	public String toString()
	{
		return "QcFile{" + "isSkeleton=" + isSkeleton + ", meshFile=" + meshFile + ", physFile=" + physFile + ", boundingFile="
				+ boundingFile + ", skeletonResource=" + skeletonResource + ", diffuseTextureResource=" + diffuseTextureResource
				+ ", normalTextureResource=" + normalTextureResource + ", specularTextureResource=" + specularTextureResource
				+ ", animations=" + animations + ", outModel=" + outModel + '}';
	}

	public String getSkeletonResourceName()
	{
		if (skeletonResource != null)
		{
			return skeletonResource;
		}

		return "skeleton.default";
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

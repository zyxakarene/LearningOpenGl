package zyx.logic.converter.smd.control.json;

import java.io.File;
import org.json.simple.JSONObject;
import zyx.UtilConstants;

public class JsonMeshTextures
{

	public static final String PROPERTY_DIFFUSE_FILE = "diffuse";
	public static final String PROPERTY_NORMAL_FILE = "normal";
	public static final String PROPERTY_SPECULAR_FILE = "specular";

	public File diffuseFile;
	public File normalFile;
	public File specularFile;

	void read(JSONObject json)
	{
		String diffusePath = JsonMethods.getString(json, PROPERTY_DIFFUSE_FILE);
		String normalPath = JsonMethods.getString(json, PROPERTY_NORMAL_FILE);
		String specularPath = JsonMethods.getString(json, PROPERTY_SPECULAR_FILE);

		String root = UtilConstants.BASE_FOLDER;
		diffuseFile = new File(root + diffusePath);
		normalFile = new File(root + normalPath);
		specularFile = new File(root + specularPath);

		if (diffuseFile.exists() == false || diffuseFile.isDirectory())
		{
			diffuseFile = null;
		}

		if (normalFile.exists() == false || normalFile.isDirectory())
		{
			normalFile = null;
		}

		if (specularFile.exists() == false || specularFile.isDirectory())
		{
			specularFile = null;
		}
	}

	void save(JSONObject json)
	{
		JsonMethods.putSourceFile(json, PROPERTY_DIFFUSE_FILE, diffuseFile);
		JsonMethods.putSourceFile(json, PROPERTY_NORMAL_FILE, normalFile);
		JsonMethods.putSourceFile(json, PROPERTY_SPECULAR_FILE, specularFile);
	}

	public String getDiffuseTextureName()
	{
		if (diffuseFile != null)
		{
			return fileToId("texture.", diffuseFile);
		}

		return "texture.default_diffuse";
	}

	public String getNormalTextureName()
	{
		if (normalFile != null)
		{
			return fileToId("normal.", normalFile);
		}

		return "normal.default_normal";
	}

	public String getSpecularTextureName()
	{
		if (specularFile != null)
		{
			return fileToId("specular.", specularFile);
		}

		return "specular.default_specular";
	}

	private String fileToId(String prefix, File file)
	{
		String root = UtilConstants.TEXTURES_FOLDER;
		String filePath = file.getAbsolutePath().replace(root, "");
		
		String id = filePath.replaceAll("\\\\", ".");
		return prefix + id;
	}
}

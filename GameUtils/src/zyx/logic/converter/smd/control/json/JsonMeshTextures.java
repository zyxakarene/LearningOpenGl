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
		JsonMethods.putFile(json, PROPERTY_DIFFUSE_FILE, diffuseFile);
		JsonMethods.putFile(json, PROPERTY_NORMAL_FILE, normalFile);
		JsonMethods.putFile(json, PROPERTY_SPECULAR_FILE, specularFile);
	}
}

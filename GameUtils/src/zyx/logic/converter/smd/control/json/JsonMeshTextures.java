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
		String meshPath = JsonMethods.getString(json, PROPERTY_DIFFUSE_FILE);
		String physPath = JsonMethods.getString(json, PROPERTY_NORMAL_FILE);
		String boundingPath = JsonMethods.getString(json, PROPERTY_SPECULAR_FILE);

		String root = UtilConstants.BASE_FOLDER;
		diffuseFile = new File(root + meshPath);
		normalFile = new File(root + physPath);
		specularFile = new File(root + boundingPath);

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

}

package zyx.logic.converter.smd.control.json;

import java.io.File;
import org.json.simple.JSONObject;
import zyx.UtilConstants;

public class JsonMeshFiles
{

	public static final String PROPERTY_MESH = "mesh";
	public static final String PROPERTY_PHYS = "phys";
	public static final String PROPERTY_BOUNDING = "bounding";

	public File meshFile;
	public File physFile;
	public File boundingFile;

	void read(JSONObject json)
	{
		String meshPath = JsonMethods.getString(json, PROPERTY_MESH);
		String physPath = JsonMethods.getString(json, PROPERTY_PHYS);
		String boundingPath = JsonMethods.getString(json, PROPERTY_BOUNDING);
		
		String root = UtilConstants.BASE_FOLDER;
		meshFile = new File(root + meshPath);
		physFile = new File(root + physPath);
		boundingFile = new File(root + boundingPath);
		
		if (meshFile.exists() == false || meshFile.isDirectory())
		{
			meshFile = null;
		}
		
		if (physFile.exists() == false || physFile.isDirectory())
		{
			physFile = null;
		}
		
		if (boundingFile.exists() == false || boundingFile.isDirectory())
		{
			boundingFile = null;
		}
	}

	void save(JSONObject json)
	{
		JsonMethods.putSourceFile(json, PROPERTY_MESH, meshFile);
		JsonMethods.putSourceFile(json, PROPERTY_PHYS, physFile);
		JsonMethods.putSourceFile(json, PROPERTY_BOUNDING, boundingFile);
	}
}

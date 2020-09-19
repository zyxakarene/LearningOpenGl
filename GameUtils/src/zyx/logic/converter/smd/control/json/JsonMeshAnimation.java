package zyx.logic.converter.smd.control.json;

import java.io.File;
import org.json.simple.JSONObject;
import zyx.UtilConstants;

public class JsonMeshAnimation
{

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_FILE = "file";
	public static final String PROPERTY_BLEND = "blend";
	public static final String PROPERTY_LOOP = "loop";
	public static final String PROPERTY_FRAMES_START = "frameStart";
	public static final String PROPERTY_FRAMES_END = "frameEnd";

	public String name;
	public File file;
	public int blend;
	public boolean loop;
	public int framesStart;
	public int framesEnd;

	void read(JSONObject json)
	{
		String filePath = JsonMethods.getString(json, PROPERTY_FILE, "missing_name");
		file = new File(UtilConstants.BASE_FOLDER + filePath);
		if (file.exists() == false || file.isDirectory())
		{
			file = null;
		}
		
		name = JsonMethods.getString(json, PROPERTY_NAME, "missing_name");
		blend = JsonMethods.getInt(json, PROPERTY_BLEND, 0);
		loop = JsonMethods.getBoolean(json, PROPERTY_LOOP, false);
		framesStart = JsonMethods.getInt(json, PROPERTY_FRAMES_START, 0);
		framesEnd = JsonMethods.getInt(json, PROPERTY_FRAMES_END, 0);
	}

	@Override
	public String toString()
	{
		String fileName = file != null ? file.getName() : "[Missing File]";
		return String.format("%s - %s Blend: %s, frames [%s - %s]", name, fileName, blend, framesStart, framesEnd);
	}
}

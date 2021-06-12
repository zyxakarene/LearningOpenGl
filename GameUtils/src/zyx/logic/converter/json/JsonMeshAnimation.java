package zyx.logic.converter.json;

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
	public short blend;
	public boolean loop;
	public short framesStart;
	public short framesEnd;

	void read(JSONObject json)
	{
		String filePath = JsonMethods.getString(json, PROPERTY_FILE);
		file = new File(UtilConstants.BASE_FOLDER + filePath);
		if (file.exists() == false || file.isDirectory())
		{
			file = null;
		}
		
		name = JsonMethods.getString(json, PROPERTY_NAME, "missing_name");
		blend = JsonMethods.getShort(json, PROPERTY_BLEND, (short) 0);
		loop = JsonMethods.getBoolean(json, PROPERTY_LOOP, false);
		framesStart = JsonMethods.getShort(json, PROPERTY_FRAMES_START, (short) 0);
		framesEnd = JsonMethods.getShort(json, PROPERTY_FRAMES_END, (short) 0);
	}

	@Override
	public String toString()
	{
		String fileName = file != null ? file.getName() : "[Missing File]";
		
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append(" - ");
		builder.append(fileName);
		if (blend > 0)
		{
			builder.append(" Blend: ");
			builder.append(blend);
		}
		
		if (isFullFileAnimation() == false)
		{
			builder.append(" Frames: [");
			builder.append(framesStart);
			builder.append(" - ");
			builder.append(framesEnd);
			builder.append("]");
		}
		
		return builder.toString();
	}

	void save(JSONObject json)
	{
		JsonMethods.putSourceFile(json, PROPERTY_FILE, file);
		json.put(PROPERTY_NAME, name);
		json.put(PROPERTY_BLEND, blend);
		json.put(PROPERTY_LOOP, loop);
		json.put(PROPERTY_FRAMES_START, framesStart);
		json.put(PROPERTY_FRAMES_END, framesEnd);
	}

	public boolean isFullFileAnimation()
	{
		return framesStart == 0 && framesEnd == Short.MAX_VALUE;
	}
	
	public void setFullFileAnimation()
	{
		framesStart = 0;
		framesEnd = Short.MAX_VALUE;
	}
}

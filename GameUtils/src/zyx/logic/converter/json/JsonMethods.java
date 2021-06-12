package zyx.logic.converter.json;

import java.io.File;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zyx.UtilConstants;

class JsonMethods
{
	static String SourceFileToJsonText(File file)
	{
		if (file != null)
		{
			String root = UtilConstants.BASE_FOLDER;
			String filePath = file.getAbsolutePath().replace(root, "");
			filePath = filePath.replaceAll("\\\\", "/");
			
			return filePath;
		}
		
		return "";
	}
	
	static void putSourceFile(JSONObject json, String name, File file)
	{
		if (file != null)
		{
			String filePath = SourceFileToJsonText(file);
			json.put(name, filePath);
		}
	}
	
	static void putOutputFile(JSONObject json, String name, File file)
	{
		if (file != null)
		{
			String root = UtilConstants.ASSETS_OUTPUT;
			String filePath = file.getAbsolutePath().replace(root, "");
			filePath = filePath.replaceAll("\\\\", "/");
			json.put(name, filePath);
		}
	}
	
	static String getString(JSONObject json, String name)
	{
		return getString(json, name, "");
	}
	
	static String getString(JSONObject json, String name, String defaultValue)
	{
		Object result = json.get(name);
		if (result instanceof String)
		{
			return (String) result;
		}
		
		return defaultValue;
	}
	
	static int getInt(JSONObject json, String name)
	{
		return getInt(json, name, 0);
	}
	
	static int getInt(JSONObject json, String name, int defaultValue)
	{
		Object result = json.get(name);
		if (result instanceof Number)
		{
			return ((Number) result).intValue();
		}
		
		return defaultValue;
	}
	
	static short getShort(JSONObject json, String name, short defaultValue)
	{
		Object result = json.get(name);
		if (result instanceof Number)
		{
			return ((Number) result).shortValue();
		}
		
		return defaultValue;
	}
	
	static byte getByte(JSONObject json, String name, byte defaultValue)
	{
		Object result = json.get(name);
		if (result instanceof Number)
		{
			return ((Number) result).byteValue();
		}
		
		return defaultValue;
	}
	
	static boolean getBoolean(JSONObject json, String name)
	{
		return getBoolean(json, name, false);
	}
	
	static boolean getBoolean(JSONObject json, String name, boolean defaultValue)
	{
		Object result = json.get(name);
		if (result instanceof Boolean)
		{
			return (boolean) result;
		}
		
		return defaultValue;
	}
	
	static JSONArray getArray(JSONObject json, String name)
	{
		Object result = json.get(name);
		if (result instanceof JSONArray)
		{
			return (JSONArray) result;
		}
		
		return new JSONArray();
	}
	
	static JSONObject getObject(JSONObject json, String name)
	{
		Object result = json.get(name);
		if (result instanceof JSONObject)
		{
			return (JSONObject) result;
		}
		
		return new JSONObject();
	}

	static JSONObject getObjectFromArray(JSONArray json, int index)
	{
		Object result = json.get(index);
		if (result instanceof JSONObject)
		{
			return (JSONObject) result;
		}
		
		return new JSONObject();
	}
}

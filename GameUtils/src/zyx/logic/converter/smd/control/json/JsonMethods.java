package zyx.logic.converter.smd.control.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class JsonMethods
{
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

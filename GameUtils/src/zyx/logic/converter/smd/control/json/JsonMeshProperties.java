package zyx.logic.converter.smd.control.json;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdSurface;

public class JsonMeshProperties
{
	public static final String PROPERTY_ENTRIES = "entries";

	public JsonMeshPropertyEntry[] entries;

	public JsonMeshProperties()
	{
		entries = new JsonMeshPropertyEntry[0];
	}
	
	void read(JSONObject jsonProperties)
	{
		JSONArray array = JsonMethods.getArray(jsonProperties, PROPERTY_ENTRIES);
		
		int len = array.size();
		entries = new JsonMeshPropertyEntry[len];
		for (int i = 0; i < len; i++)
		{
			JSONObject jsonEntry = JsonMethods.getObjectFromArray(array, i);
			JsonMeshPropertyEntry entry = new JsonMeshPropertyEntry();
			entry.read(jsonEntry);
			
			entries[i] = entry;
		}
	}

	void save(JSONObject json)
	{
		JSONArray array = new JSONArray();
		for (int i = 0; i < entries.length; i++)
		{
			JSONObject jsonEntry = new JSONObject();
			entries[i].save(jsonEntry);
			
			array.add(jsonEntry);
		}
		
		json.put(PROPERTY_ENTRIES, array);
	}

	public void setSize(HashMap<String, ParsedSmdSurface> surfaces)
	{
		JsonMeshPropertyEntry[] clone = Arrays.copyOf(entries, entries.length);
		Set<String> keys = surfaces.keySet();
		
		entries = new JsonMeshPropertyEntry[surfaces.size()];
		
		Iterator<String> keyIterator = keys.iterator();
		
		for (int i = 0; i < entries.length; i++)
		{
			String key = keyIterator.next();
			entries[i] = findFrom(key, clone);
		}
	}

	private JsonMeshPropertyEntry findFrom(String key, JsonMeshPropertyEntry[] list)
	{
		for (JsonMeshPropertyEntry entry : list)
		{
			if (entry.name.equals(key))
			{
				return entry;
			}
		}
		
		JsonMeshPropertyEntry entry = new JsonMeshPropertyEntry();
		entry.name = key;
		return entry;
	}
}

package zyx.logic.converter.json;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zyx.logic.converter.smd.parsedVo.ParsedSmdSurface;

public class JsonMeshTextures
{
	public static final String PROPERTY_ENTRIES = "entries";

	public JsonMeshTextureEntry[] entries;

	public JsonMeshTextures()
	{
		entries = new JsonMeshTextureEntry[0];
	}
	
	void read(JSONObject json)
	{
		JSONArray entries = JsonMethods.getArray(json, PROPERTY_ENTRIES);
		
		int len = entries.size();
		this.entries = new JsonMeshTextureEntry[len];
		
		for (int i = 0; i < len; i++)
		{
			JSONObject jsonEntry = JsonMethods.getObjectFromArray(entries, i);
			JsonMeshTextureEntry textureEntry = new JsonMeshTextureEntry();
			
			textureEntry.read(jsonEntry);
			this.entries[i] = textureEntry;
		}
	}

	void save(JSONObject json)
	{
		JSONArray array = new JSONArray();
		for (JsonMeshTextureEntry textureEntry : entries)
		{
			JSONObject jsonEntry = new JSONObject();
			textureEntry.save(jsonEntry);
			
			array.add(jsonEntry);
		}
		
		json.put(PROPERTY_ENTRIES, array);
	}

	public void setSize(HashMap<String, ParsedSmdSurface> surfaces)
	{
		JsonMeshTextureEntry[] clone = Arrays.copyOf(entries, entries.length);
		Set<String> keys = surfaces.keySet();
		
		entries = new JsonMeshTextureEntry[surfaces.size()];
		
		Iterator<String> keyIterator = keys.iterator();
		
		for (int i = 0; i < entries.length; i++)
		{
			String key = keyIterator.next();
			entries[i] = findFrom(key, clone);
		}
	}

	private JsonMeshTextureEntry findFrom(String key, JsonMeshTextureEntry[] list)
	{
		for (JsonMeshTextureEntry entry : list)
		{
			if (entry.name.equals(key))
			{
				return entry;
			}
		}
		
		JsonMeshTextureEntry entry = new JsonMeshTextureEntry();
		entry.name = key;
		return entry;
	}
}

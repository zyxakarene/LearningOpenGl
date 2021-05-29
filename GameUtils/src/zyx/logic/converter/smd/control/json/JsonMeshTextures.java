package zyx.logic.converter.smd.control.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonMeshTextures
{
	public static final String PROPERTY_ENTRIES = "entries";

	public JsonMeshTextureEntry[] textureEntries;

	void read(JSONObject json)
	{
		JSONArray entries = JsonMethods.getArray(json, PROPERTY_ENTRIES);
		
		int len = entries.size();
		textureEntries = new JsonMeshTextureEntry[len];
		
		for (int i = 0; i < len; i++)
		{
			JSONObject jsonEntry = JsonMethods.getObjectFromArray(entries, i);
			JsonMeshTextureEntry textureEntry = new JsonMeshTextureEntry();
			
			textureEntry.read(jsonEntry);
			textureEntries[i] = textureEntry;
		}
	}

	void save(JSONObject json)
	{
		JSONArray array = new JSONArray();
		for (JsonMeshTextureEntry textureEntry : textureEntries)
		{
			JSONObject jsonEntry = new JSONObject();
			textureEntry.save(jsonEntry);
		}
		
		json.put(PROPERTY_ENTRIES, array);
	}
}

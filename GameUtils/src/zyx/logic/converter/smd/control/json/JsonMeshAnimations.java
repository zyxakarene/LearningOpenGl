package zyx.logic.converter.smd.control.json;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonMeshAnimations
{

	public ArrayList<JsonMeshAnimation> animations;

	public JsonMeshAnimations()
	{
		animations = new ArrayList();
	}

	void read(JSONArray json)
	{
		int len = json.size();
		for (int i = 0; i < len; i++)
		{
			JSONObject jsonAnim = JsonMethods.getObjectFromArray(json, i);
			
			JsonMeshAnimation anim = new JsonMeshAnimation();
			anim.read(jsonAnim);
			
			animations.add(anim);
		}
	}

	void save(JSONArray json)
	{
		for (JsonMeshAnimation animation : animations)
		{
			JSONObject jsonAnim = new JSONObject();
			animation.save(jsonAnim);
			
			json.add(jsonAnim);
		}
	}
}

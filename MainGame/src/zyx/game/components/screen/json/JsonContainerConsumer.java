package zyx.game.components.screen.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zyx.engine.components.screen.DisplayObjectContainer;

public class JsonContainerConsumer extends JsonBaseConsumer<DisplayObjectContainer>
{

	protected static final String CHILDREN = "children";
	
	@Override
	public void postConsume(JSONObject json)
	{
		Object children = json.get(CHILDREN);
		
		if (children != null)
		{
			JSONArray array = (JSONArray) children;
			int len = array.size();
			for (int i = 0; i < len; i++)
			{
				JSONObject obj = (JSONObject) array.get(i);
				JsonSpriteParser.getInstance().createSprite(currentDisplayObject, obj);
			}
		}
		
		super.postConsume(json);
	}
}

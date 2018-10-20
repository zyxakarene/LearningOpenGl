package zyx.game.components.screen.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;

public class JsonContainerConsumer<T extends DisplayObjectContainer> extends JsonBaseConsumer<T>
{

	protected static final String CHILDREN = "children";
	protected static final String FORCE_WIDTH = "forceWidth";
	protected static final String FORCE_HEIGHT = "forceHeight";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case FORCE_WIDTH:
				currentDisplayObject.forceWidth = toFloat(value);
				break;
			case FORCE_HEIGHT:
				currentDisplayObject.forceHeight = toFloat(value);
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}
	
	
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

package zyx.game.components.screen.json;

import java.util.function.BiConsumer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.DisplayObject;
import zyx.utils.GameConstants;

class JsonBaseConsumer<T extends DisplayObject> implements BiConsumer<String, Object>
{

	protected static final Vector3f HELPER_VECTOR = new Vector3f();
	
	protected static final String NAME = "name";
	protected static final String CHILDREN = "children";
	protected static final String X = "x";
	protected static final String Y = "y";
	protected static final String WIDTH = "width";
	protected static final String HEIGHT = "height";
	protected static final String PERCENT_WIDTH = "percentWidth";
	protected static final String PERCENT_HEIGHT = "percentHeight";
	protected static final String ROTATION = "rotation";

	protected T currentDisplayObject;

	final void consume(T container, JSONObject json)
	{
		currentDisplayObject = container;
		
		if (json.containsKey(NAME))
		{
			String name = json.get(NAME).toString();
			currentDisplayObject.name = name;
		}
		
		json.forEach(this);
		
		onPostConsume(json);
	}
	
	@Override
	public final void accept(String name, Object value)
	{
		switch (name)
		{
			case X:
				currentDisplayObject.setX(toFloat(value));
				break;
			case Y:
				currentDisplayObject.setY(toFloat(value));
				break;
			case WIDTH:
				currentDisplayObject.setWidth(toFloat(value));
				break;
			case HEIGHT:
				currentDisplayObject.setHeight(toFloat(value));
				break;
			case ROTATION:
				currentDisplayObject.setRotation(toFloat(value));
				break;
			case CHILDREN:
				JsonSprite container = (JsonSprite) currentDisplayObject;
				JSONArray array = (JSONArray) value;
				int len = array.size();
				for (int i = 0; i < len; i++)
				{
					JSONObject obj = (JSONObject) array.get(i);
					JsonSpriteParser.getInstance().createSprite(container, obj);
				}
				break;
			default:
				onAccept(name, value);
				break;
		}
	}

	protected float toFloat(Object value)
	{
		if (value instanceof Float)
		{
			return (Float) value;
		}
		
		if (value instanceof Number)
		{
			Number num = (Number) value;
			return num.floatValue();
		}
		
		throw new IllegalArgumentException("Invalid parameter: " + value);
	}
	
	protected void onAccept(String name, Object value)
	{
	}

	protected void onPostConsume(JSONObject json)
	{
		Object percentWidth = json.get(PERCENT_WIDTH);
		Object percentHeight = json.get(PERCENT_HEIGHT);
		
		if (percentWidth != null)
		{
			currentDisplayObject.setWidth(toFloat(percentWidth) * GameConstants.GAME_WIDTH);
		}
		
		if(percentHeight != null)
		{
			currentDisplayObject.setHeight(toFloat(percentHeight) * GameConstants.GAME_HEIGHT);
		}
	}
}

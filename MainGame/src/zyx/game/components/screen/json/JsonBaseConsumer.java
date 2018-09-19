package zyx.game.components.screen.json;

import java.util.function.BiConsumer;
import static jdk.nashorn.internal.runtime.JSType.toNumber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zyx.engine.components.screen.DisplayObject;

class JsonBaseConsumer<T extends DisplayObject> implements BiConsumer<String, Object>
{

	protected static final String NAME = "name";
	protected static final String CHILDREN = "children";
	protected static final String X = "x";
	protected static final String Y = "y";
	protected static final String WIDTH = "width";
	protected static final String HEIGHT = "height";

	protected T currentDisplayObject;

	final void consume(T container, JSONObject json)
	{
		currentDisplayObject = container;
		json.forEach(this);
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
			case NAME:
				currentDisplayObject.name = value.toString();
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
}

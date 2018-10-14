package zyx.game.components.screen.json;

import java.util.function.BiConsumer;
import org.json.simple.JSONObject;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.components.screen.DisplayObject;
import zyx.utils.GameConstants;

class JsonBaseConsumer<T extends DisplayObject> implements BiConsumer<String, Object>
{

	protected static final Vector2f HELPER_VECTOR_2 = new Vector2f();
	protected static final Vector3f HELPER_VECTOR_3 = new Vector3f();
	protected static final Vector4f HELPER_VECTOR_4 = new Vector4f();

	protected static final String NAME = "name";
	protected static final String X = "x";
	protected static final String Y = "y";
	protected static final String WIDTH = "width";
	protected static final String HEIGHT = "height";
	protected static final String PERCENT_WIDTH = "percentWidth";
	protected static final String PERCENT_HEIGHT = "percentHeight";
	protected static final String CENTER_OFFSET_X = "centerOffsetX";
	protected static final String CENTER_OFFSET_Y = "centerOffsetY";
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

	public void postConsume(JSONObject json)
	{
		Object percentWidth = json.get(PERCENT_WIDTH);
		Object percentHeight = json.get(PERCENT_HEIGHT);

		if (percentWidth != null)
		{
			currentDisplayObject.setWidth(toFloat(percentWidth) * GameConstants.GAME_WIDTH);
		}

		if (percentHeight != null)
		{
			currentDisplayObject.setHeight(toFloat(percentHeight) * GameConstants.GAME_HEIGHT);
		}

		Object centerX = json.get(CENTER_OFFSET_X);
		Object centerY = json.get(CENTER_OFFSET_Y);
		
		if(centerX != null || centerY != null)
		{
			currentDisplayObject.getPosition(false, HELPER_VECTOR_2);
			float posX = HELPER_VECTOR_2.x;
			float posY = HELPER_VECTOR_2.y;
			
			if (centerX != null)
			{
				float offsetX = toFloat(centerX);
				float stageWidth = GameConstants.GAME_WIDTH;
				float currentWidth = currentDisplayObject.getWidth();

				posX = (stageWidth/2) - (currentWidth / 2) + offsetX;
			}

			if (centerY != null)
			{
				float offsetY = toFloat(centerY);
				float stageHeight = GameConstants.GAME_HEIGHT;
				float currentHeight = currentDisplayObject.getHeight();

				posY = (stageHeight/2) - (currentHeight / 2) + offsetY;
			}
			
			currentDisplayObject.setPosition(false, posX, posY);
		}

	}
}

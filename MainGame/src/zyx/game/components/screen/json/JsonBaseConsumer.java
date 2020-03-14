package zyx.game.components.screen.json;

import java.util.function.BiConsumer;
import org.json.simple.JSONObject;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.utils.ScreenSize;
import zyx.utils.Color;

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
	protected static final String ROTATION = "rotation";
	protected static final String PIVOT_X = "pivotX";
	protected static final String PIVOT_Y = "pivotY";

	protected static final String PERCENT_WIDTH = "percentWidth";
	protected static final String PERCENT_HEIGHT = "percentHeight";
	protected static final String CENTER_OFFSET_X = "centerOffsetX";
	protected static final String CENTER_OFFSET_Y = "centerOffsetY";
	protected static final String TOP_DOCK = "topDock";
	protected static final String BOTTOM_DOCK = "bottomDock";
	protected static final String LEFT_DOCK = "leftDock";
	protected static final String RIGHT_DOCK = "rightDock";

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
			case PIVOT_X:
				currentDisplayObject.setPivotX(toFloat(value));
				break;
			case PIVOT_Y:
				currentDisplayObject.setPivotY(toFloat(value));
				break;
			case NAME:
				currentDisplayObject.name = value.toString();
				break;
			default:
				onAccept(name, value);
				break;
		}
	}

	protected boolean toBoolean(Object value)
	{
		if (value instanceof Boolean)
		{
			return (Boolean) value;
		}

		throw new IllegalArgumentException("Invalid parameter: " + value);
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

	protected void toColor(Object value, Vector3f out)
	{
		String hexColor = value.toString();
		hexColor = hexColor.replace("0x", "");
		int color = Integer.parseInt(hexColor, 16);

		Color.toVector(color, out);
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
			currentDisplayObject.setWidth(toFloat(percentWidth) * ScreenSize.width);
		}

		if (percentHeight != null)
		{
			currentDisplayObject.setHeight(toFloat(percentHeight) * ScreenSize.height);
		}

		Object centerX = json.get(CENTER_OFFSET_X);
		Object centerY = json.get(CENTER_OFFSET_Y);

		if (centerX != null || centerY != null)
		{
			currentDisplayObject.getPosition(false, HELPER_VECTOR_2);
			float posX = HELPER_VECTOR_2.x;
			float posY = HELPER_VECTOR_2.y;

			if (centerX != null)
			{
				float offsetX = toFloat(centerX);
				float stageWidth = ScreenSize.width;
				float currentWidth = currentDisplayObject.getWidth();

				posX = (stageWidth / 2) - (currentWidth / 2) + offsetX;
			}

			if (centerY != null)
			{
				float offsetY = toFloat(centerY);
				float stageHeight = ScreenSize.height;
				float currentHeight = currentDisplayObject.getHeight();

				posY = (stageHeight / 2) - (currentHeight / 2) + offsetY;
			}

			currentDisplayObject.setPosition(false, posX, posY);
		}

		Object topDock = json.get(TOP_DOCK);
		Object bottomDock = json.get(BOTTOM_DOCK);
		Object leftDock = json.get(LEFT_DOCK);
		Object rightDock = json.get(RIGHT_DOCK);

		if (topDock != null || bottomDock != null || leftDock != null || rightDock != null)
		{
			currentDisplayObject.getPosition(false, HELPER_VECTOR_2);
			float posX = HELPER_VECTOR_2.x;
			float posY = HELPER_VECTOR_2.y;

			if (topDock != null)
			{
				posY = toFloat(topDock);
			}
			else if (bottomDock != null)
			{
				float height = currentDisplayObject.getHeight();
				posY = ScreenSize.height - height - toFloat(bottomDock);
			}

			if (leftDock != null)
			{
				posX = toFloat(leftDock);
			}
			else if (rightDock != null)
			{
				float width = currentDisplayObject.getWidth();
				posX = ScreenSize.width - width - toFloat(rightDock);
			}

			currentDisplayObject.setPosition(false, posX, posY);
		}
	}
}

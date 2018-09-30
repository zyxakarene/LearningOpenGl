package zyx.game.components.screen.json;

import org.json.simple.JSONObject;
import zyx.engine.components.screen.AbstractImage;
import zyx.engine.components.screen.Button;
import zyx.engine.components.screen.Checkbox;
import zyx.engine.components.screen.DisplayObject;
import zyx.engine.components.screen.Image;
import zyx.engine.components.screen.Scale9Image;

class JsonSpriteParser
{

	private static final JsonSpriteParser instance = new JsonSpriteParser();

	private static final String TYPE = "type";

	private static final String TYPE_CONTAINER = "container";
	private static final String TYPE_IMAGE = "image";
	private static final String TYPE_SCALE_9_IMAGE = "scale9image";
	private static final String TYPE_BUTTON = "button";
	private static final String TYPE_SCALE_9_BUTTON = "scale9button";
	private static final String TYPE_CHECKBOX = "checkbox";
	private static final String TYPE_SCALE_9_CHECKBOX = "scale9checkbox";

	static JsonSpriteParser getInstance()
	{
		return instance;
	}

	private int currentChildDepth;

	void createSpriteFrom(JsonSprite parent, JSONObject json)
	{
		currentChildDepth = 0;

		createSprite(parent, json);
	}

	void createSprite(JsonSprite parent, JSONObject json)
	{
		String type = (String) json.get(TYPE);

		switch (type)
		{
			case TYPE_CONTAINER:
				getOrCreateContainer(parent, json);
				break;
			case TYPE_IMAGE:
				createImage(parent, json, false);
				break;
			case TYPE_SCALE_9_IMAGE:
				createImage(parent, json, true);
				break;
			case TYPE_BUTTON:
				createButton(parent, json, false);
				break;
			case TYPE_SCALE_9_BUTTON:
				createButton(parent, json, true);
				break;
			case TYPE_CHECKBOX:
				createCheckbox(parent, json, false);
				break;
			case TYPE_SCALE_9_CHECKBOX:
				createCheckbox(parent, json, true);
				break;
			default:
				throw new AssertionError("Unknown type:" + type);
		}
	}

	private void getOrCreateContainer(JsonSprite parent, JSONObject json)
	{
		JsonSprite child = (currentChildDepth == 0) ? parent : new JsonSprite();
		new JsonContainerConsumer().consume(child, json);
	}

	private void createImage(JsonSprite parent, JSONObject json, boolean scale9)
	{
		AbstractImage image;
		if (scale9)
		{
			image = new Scale9Image();
		}
		else
		{
			image = new Image();
		}

		new JsonImageConsumer().consume(image, json);
		
		parent.addChild(image);
	}

	private void createButton(JsonSprite parent, JSONObject json, boolean scale9)
	{
		Button button = new Button(scale9);

		new JsonButtonConsumer().consume(button, json);
		
		parent.addChild(button);
	}

	private void createCheckbox(JsonSprite parent, JSONObject json, boolean scale9)
	{
		Checkbox checkbox = new Checkbox(scale9);

		new JsonCheckboxConsumer().consume(checkbox, json);
		
		parent.addChild(checkbox);
	}
}

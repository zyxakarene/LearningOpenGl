package zyx.game.components.screen.json;

import org.json.simple.JSONObject;
import zyx.engine.components.screen.*;

class JsonSpriteParser
{

	private static final JsonSpriteParser instance = new JsonSpriteParser();

	private static final String TYPE = "type";

	static final String TYPE_CONTAINER = "container";
	static final String TYPE_QUAD = "quad";
	static final String TYPE_IMAGE = "image";
	static final String TYPE_SCALE_NINE_IMAGE = "scale9image";
	static final String TYPE_BUTTON = "button";
	static final String TYPE_SCALE_NINE_BUTTON = "scale9button";
	static final String TYPE_CHECKBOX = "checkbox";
	static final String TYPE_SCALE_NINE_CHECKBOX = "scale9checkbox";

	private ConsumerFactory factory;
	private int currentChildDepth;
	
	static JsonSpriteParser getInstance()
	{
		return instance;
	}

	private JsonSpriteParser()
	{
		factory = new ConsumerFactory();
		currentChildDepth = 0;
	}

	void createSpriteFrom(DisplayObjectContainer parent, JSONObject json)
	{
		currentChildDepth = 0;

		createSprite(parent, json);
	}

	void createSprite(DisplayObjectContainer parent, JSONObject json)
	{
		String type = (String) json.get(TYPE);
		DisplayObject child;
		
		switch (type)
		{
			case TYPE_CONTAINER:
				child = (currentChildDepth == 0) ? parent : new JsonSprite();
				currentChildDepth++;
				break;
			case TYPE_IMAGE:
				child = new Image();
				break;
			case TYPE_SCALE_NINE_IMAGE:
				child = new Scale9Image();
				break;
			case TYPE_BUTTON:
				child = new Button(false);
				break;
			case TYPE_SCALE_NINE_BUTTON:
				child = new Button(true);
				break;
			case TYPE_CHECKBOX:
				child = new Checkbox(false);
				break;
			case TYPE_SCALE_NINE_CHECKBOX:
				child = new Checkbox(true);
				break;
			case TYPE_QUAD:
				child = new Quad(1, 1, 0xFFFFFF);
				break;
			default:
				throw new AssertionError("Unknown type:" + type);
		}
		
		factory.consumeByType(type, parent, child, json);
	}
}

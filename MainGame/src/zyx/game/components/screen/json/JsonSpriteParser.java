package zyx.game.components.screen.json;

import org.json.simple.JSONObject;
import zyx.engine.components.screen.*;

class JsonSpriteParser
{

	private static final JsonSpriteParser instance = new JsonSpriteParser();

	private static final String TYPE = "type";

	private static final String TYPE_CONTAINER = "container";
	private static final String TYPE_QUAD = "quad";
	private static final String TYPE_IMAGE = "image";
	private static final String TYPE_SCALE_NINE_IMAGE = "scale9image";
	private static final String TYPE_BUTTON = "button";
	private static final String TYPE_SCALE_NINE_BUTTON = "scale9button";
	private static final String TYPE_CHECKBOX = "checkbox";
	private static final String TYPE_SCALE_NINE_CHECKBOX = "scale9checkbox";

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
		JsonBaseConsumer consumer;
		
		switch (type)
		{
			case TYPE_CONTAINER:
				consumer = getOrCreateContainer(parent, json);
				break;
			case TYPE_IMAGE:
				consumer = createImage(parent, json, false);
				break;
			case TYPE_SCALE_NINE_IMAGE:
				consumer = createImage(parent, json, true);
				break;
			case TYPE_BUTTON:
				consumer = createButton(parent, json, false);
				break;
			case TYPE_SCALE_NINE_BUTTON:
				consumer = createButton(parent, json, true);
				break;
			case TYPE_CHECKBOX:
				consumer = createCheckbox(parent, json, false);
				break;
			case TYPE_SCALE_NINE_CHECKBOX:
				consumer = createCheckbox(parent, json, true);
				break;
			case TYPE_QUAD:
				consumer = createQuad(parent, json);
				break;
			default:
				throw new AssertionError("Unknown type:" + type);
		}
		
		consumer.postConsume(json);
	}

	private JsonBaseConsumer getOrCreateContainer(DisplayObjectContainer parent, JSONObject json)
	{
		DisplayObjectContainer child = (currentChildDepth == 0) ? parent : new JsonSprite();
		
		currentChildDepth++;
				
		JsonContainerConsumer consumer = new JsonContainerConsumer();
		consumer.consume(child, json);
		
		if(parent != child)
		{
			parent.addChild(child);
		}
		
		return consumer;
	}

	private JsonBaseConsumer createImage(DisplayObjectContainer parent, JSONObject json, boolean scale9)
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

		JsonImageConsumer consumer = new JsonImageConsumer();
		consumer.consume(image, json);
		
		parent.addChild(image);
		
		return consumer;
	}

	private JsonBaseConsumer createButton(DisplayObjectContainer parent, JSONObject json, boolean scale9)
	{
		Button button = new Button(scale9);

		JsonButtonConsumer consumer = new JsonButtonConsumer();
		consumer.consume(button, json);
		
		parent.addChild(button);
		
		return consumer;
	}

	private JsonBaseConsumer createCheckbox(DisplayObjectContainer parent, JSONObject json, boolean scale9)
	{
		Checkbox checkbox = new Checkbox(scale9);

		JsonCheckboxConsumer consumer = new JsonCheckboxConsumer();
		consumer.consume(checkbox, json);
		
		parent.addChild(checkbox);
		
		return consumer;
	}

	private JsonBaseConsumer createQuad(DisplayObjectContainer parent, JSONObject json)
	{
		Quad quad = new Quad(1, 1, 0xFFFFFF);
		
		JsonQuadConsumer consumer = new JsonQuadConsumer();
		consumer.consume(quad, json);

		parent.addChild(quad);
		
		return consumer;
	}
}

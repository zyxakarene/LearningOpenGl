package zyx.game.components.screen.json;

import org.json.simple.JSONObject;
import zyx.engine.components.screen.Image;
import zyx.engine.components.screen.Scale9Image;

class JsonSpriteParser
{

	private static final JsonSpriteParser instance = new JsonSpriteParser();

	private static final String TYPE = "type";

	private static final String TYPE_CONTAINER = "container";
	private static final String TYPE_IMAGE = "image";
	private static final String TYPE_SCALE_9_IMAGE = "scale9image";

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
				createImage(parent, json);
				break;
			case TYPE_SCALE_9_IMAGE:
				createScale9Image(parent, json);
				break;
			default:
				throw new AssertionError();
		}
	}

	private void getOrCreateContainer(JsonSprite parent, JSONObject json)
	{
		JsonSprite child = (currentChildDepth == 0) ? parent : new JsonSprite();
		new JsonContainerConsumer().consume(child, json);
	}

	private void createImage(JsonSprite parent, JSONObject json)
	{
		Image image = new Image();
		parent.addChild(image);
		
		new JsonImageConsumer().consume(image, json);
	}

	private void createScale9Image(JsonSprite parent, JSONObject json)
	{
		Scale9Image image = new Scale9Image();
		parent.addChild(image);
		
		new JsonScale9ImageConsumer().consume(image, json);
	}
}

package zyx.game.components.screen.json;

import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.interactable.Checkbox;
import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import org.json.simple.JSONObject;
import zyx.engine.components.screen.list.ItemList;
import zyx.engine.components.screen.text.Textfield;

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
	static final String TYPE_ITEM_LIST = "itemList";
	static final String TYPE_TEXTFIELD = "textfield";

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
				child = (currentChildDepth == 0) ? parent : new DisplayObjectContainer();
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
			case TYPE_ITEM_LIST:
				child = new ItemList();
				break;
			case TYPE_TEXTFIELD:
				child = new Textfield();
				break;
			default:
				throw new AssertionError("Unknown type:" + type);
		}
		
		factory.consumeByType(type, parent, child, json);
	}
}

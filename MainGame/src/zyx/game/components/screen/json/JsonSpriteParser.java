package zyx.game.components.screen.json;

import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.interactable.Checkbox;
import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import org.json.simple.JSONObject;
import zyx.engine.components.screen.composed.ComposedImage;
import zyx.engine.components.screen.list.ItemList;
import zyx.engine.components.screen.text.Textfield;
import zyx.game.components.screen.radial.RadialMenu;
import zyx.game.components.screen.radial.RadialMenuItemRenderer;

class JsonSpriteParser
{

	private static final JsonSpriteParser INSTANCE = new JsonSpriteParser();

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
	static final String TYPE_COMPOSED_IMAGE = "composedImage";
	static final String TYPE_COMPOSED_BUTTON = "composedButton";
	static final String TYPE_COMPOSED_SCALE_NINE_IMAGE = "composedScale9image";
	static final String TYPE_COMPOSED_SCALE_NINE_BUTTON = "composedScale9button";
	
	static final String TYPE_RADIAL_BUTTON = "radialMenuButton";
	static final String TYPE_RADIAL_MENU = "radialMenu";

	private ConsumerFactory factory;
	private int currentChildDepth;
	
	static JsonSpriteParser getInstance()
	{
		return INSTANCE;
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
		String type = String.valueOf(json.get(TYPE));
		DisplayObject child = null;
		
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
			case TYPE_COMPOSED_BUTTON:
			case TYPE_BUTTON:
				child = new Button(false);
				break;
			case TYPE_COMPOSED_SCALE_NINE_BUTTON:
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
			case TYPE_COMPOSED_IMAGE:
				child = new ComposedImage(false);
				break;
			case TYPE_COMPOSED_SCALE_NINE_IMAGE:
				child = new ComposedImage(true);
				break;
			case TYPE_RADIAL_MENU:
				child = new RadialMenu();
				break;
			case TYPE_RADIAL_BUTTON:
				child = new RadialMenuItemRenderer(json.get("data"));
				break;
		}
		
		if (child != null)
		{
			factory.consumeByType(type, parent, child, json);
		}
	}
}

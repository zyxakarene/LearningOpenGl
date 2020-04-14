package zyx.game.components.screen.json;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import org.json.simple.JSONObject;
import zyx.utils.pooling.ObjectPool;

class ConsumerFactory
{
	private ObjectPool<JsonButtonConsumer> buttons;
	private ObjectPool<JsonCheckboxConsumer> checkboxes;
	private ObjectPool<JsonContainerConsumer> containers;
	private ObjectPool<JsonImageConsumer> images;
	private ObjectPool<JsonQuadConsumer> quads;
	private ObjectPool<JsonItemListConsumer> itemLists;
	private ObjectPool<JsonTextfieldConsumer> textfields;
	private ObjectPool<JsonComposedImageConsumer> composedImages;
	private ObjectPool<JsonComposedButtonConsumer> composedButtons;

	ConsumerFactory()
	{
		buttons = new ObjectPool<>(JsonButtonConsumer.class, 3);
		checkboxes = new ObjectPool<>(JsonCheckboxConsumer.class, 3);
		containers = new ObjectPool<>(JsonContainerConsumer.class, 3);
		images = new ObjectPool<>(JsonImageConsumer.class, 3);
		quads = new ObjectPool<>(JsonQuadConsumer.class, 3);
		itemLists = new ObjectPool<>(JsonItemListConsumer.class, 1);
		textfields = new ObjectPool<>(JsonTextfieldConsumer.class, 1);
		composedImages = new ObjectPool<>(JsonComposedImageConsumer.class, 1);
		composedButtons = new ObjectPool<>(JsonComposedButtonConsumer.class, 1);
	}

	void consumeByType(String type, DisplayObjectContainer parent, DisplayObject child, JSONObject json)
	{
		ObjectPool consumerPool = getConsumerByType(type);
		
		JsonBaseConsumer consumer = (JsonBaseConsumer) consumerPool.getInstance();
		
		consumer.consume(child, json);
		
		if (parent != child)
		{
			parent.addChild(child);
		}
		
		consumer.postConsume(json);

		consumerPool.releaseInstance(consumer);
	}

	private ObjectPool<? extends JsonBaseConsumer> getConsumerByType(String type)
	{
		switch (type)
		{
			case JsonSpriteParser.TYPE_CONTAINER:
			case JsonSpriteParser.TYPE_RADIAL_MENU:
				return containers;
			case JsonSpriteParser.TYPE_IMAGE:
			case JsonSpriteParser.TYPE_SCALE_NINE_IMAGE:
				return images;
			case JsonSpriteParser.TYPE_BUTTON:
			case JsonSpriteParser.TYPE_SCALE_NINE_BUTTON:
				return buttons;
			case JsonSpriteParser.TYPE_CHECKBOX:
			case JsonSpriteParser.TYPE_SCALE_NINE_CHECKBOX:
				return checkboxes;
			case JsonSpriteParser.TYPE_QUAD:
				return quads;
			case JsonSpriteParser.TYPE_ITEM_LIST:
				return itemLists;
			case JsonSpriteParser.TYPE_TEXTFIELD:
				return textfields;
			case JsonSpriteParser.TYPE_COMPOSED_IMAGE:
			case JsonSpriteParser.TYPE_COMPOSED_SCALE_NINE_IMAGE:
				return composedImages;
			case JsonSpriteParser.TYPE_COMPOSED_BUTTON:
			case JsonSpriteParser.TYPE_COMPOSED_SCALE_NINE_BUTTON:
				return composedButtons;
			default:
				throw new AssertionError("Unknown type:" + type);
		}
	}
	
}

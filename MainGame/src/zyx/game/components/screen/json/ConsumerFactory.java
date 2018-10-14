package zyx.game.components.screen.json;

import org.json.simple.JSONObject;
import zyx.engine.components.screen.*;
import zyx.utils.pooling.ObjectPool;

class ConsumerFactory
{
	private ObjectPool<JsonButtonConsumer> buttons;
	private ObjectPool<JsonCheckboxConsumer> checkboxes;
	private ObjectPool<JsonContainerConsumer> containers;
	private ObjectPool<JsonImageConsumer> images;
	private ObjectPool<JsonQuadConsumer> quads;

	ConsumerFactory()
	{
		buttons = new ObjectPool<>(JsonButtonConsumer.class, 3);
		checkboxes = new ObjectPool<>(JsonCheckboxConsumer.class, 3);
		containers = new ObjectPool<>(JsonContainerConsumer.class, 3);
		images = new ObjectPool<>(JsonImageConsumer.class, 3);
		quads = new ObjectPool<>(JsonQuadConsumer.class, 3);
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
				return containers;
			case JsonSpriteParser. TYPE_IMAGE:
			case JsonSpriteParser. TYPE_SCALE_NINE_IMAGE:
				return images;
			case JsonSpriteParser. TYPE_BUTTON:
			case JsonSpriteParser. TYPE_SCALE_NINE_BUTTON:
				return buttons;
			case JsonSpriteParser. TYPE_CHECKBOX:
			case JsonSpriteParser. TYPE_SCALE_NINE_CHECKBOX:
				return checkboxes;
			case JsonSpriteParser. TYPE_QUAD:
				return quads;
			default:
				throw new AssertionError("Unknown type:" + type);
		}
	}
	
}
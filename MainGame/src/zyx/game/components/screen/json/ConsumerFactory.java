package zyx.game.components.screen.json;

import org.json.simple.JSONObject;
import zyx.engine.components.screen.DisplayObject;
import zyx.engine.components.screen.DisplayObjectContainer;
import zyx.utils.pooling.GenericPool;

class ConsumerFactory
{
	private GenericPool<JsonButtonConsumer> buttons;
	private GenericPool<JsonCheckboxConsumer> checkboxes;
	private GenericPool<JsonContainerConsumer> containers;
	private GenericPool<JsonImageConsumer> images;
	private GenericPool<JsonQuadConsumer> quads;

	ConsumerFactory()
	{
		buttons = new GenericPool<>(JsonButtonConsumer.class, 3);
		checkboxes = new GenericPool<>(JsonCheckboxConsumer.class, 3);
		containers = new GenericPool<>(JsonContainerConsumer.class, 3);
		images = new GenericPool<>(JsonImageConsumer.class, 3);
		quads = new GenericPool<>(JsonQuadConsumer.class, 3);
	}

	void consumeByType(String type, DisplayObjectContainer parent, DisplayObject child, JSONObject json)
	{
	}
	
}

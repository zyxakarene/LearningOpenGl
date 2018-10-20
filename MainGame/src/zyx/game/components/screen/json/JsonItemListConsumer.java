package zyx.game.components.screen.json;

import java.util.HashMap;
import zyx.engine.components.screen.list.ItemList;
import zyx.engine.components.screen.list.ItemRenderer;

public class JsonItemListConsumer extends JsonContainerConsumer<ItemList>
{

	private static HashMap<String, Class<ItemRenderer>> ITEM_RENDERER_MAP = createRendererMap();

	protected static final String ITEM_RENDERER = "itemRenderer";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case ITEM_RENDERER:
				String key = value.toString();
				Class clazz = ITEM_RENDERER_MAP.get(key);
				currentDisplayObject.setItemRenderer(clazz);
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

	private static HashMap<String, Class<ItemRenderer>> createRendererMap()
	{
		HashMap<String, Class<ItemRenderer>> map = new HashMap<>();
		
		map.put("renderer", ItemRenderer.class);
		
		return map;
	}

}

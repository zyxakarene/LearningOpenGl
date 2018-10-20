package zyx.game.components.screen.hud;

import java.util.ArrayList;
import zyx.engine.components.screen.image.AbstractImage;
import zyx.engine.components.screen.list.ItemList;
import zyx.game.components.screen.json.JsonSprite;

public class MainHud extends JsonSprite
{

	private AbstractImage image;
	private ItemList list;

	@Override
	public String getResource()
	{
		return "json.test";
	}

	@Override
	protected void onComponentsCreated()
	{
		image = this.<AbstractImage>getComponentByName("test_image_1");
		list = this.<ItemList>getComponentByName("list_test");
	}

	@Override
	protected void onInitialize()
	{
		ArrayList<Integer> data = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			data.add((int)(Math.random() * 0xFFFFFF));
		}

		//list.setData(data);
	}
}

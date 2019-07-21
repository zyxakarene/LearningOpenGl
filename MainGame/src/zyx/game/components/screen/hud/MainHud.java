package zyx.game.components.screen.hud;

import java.util.ArrayList;
import zyx.engine.components.screen.image.AbstractImage;
import zyx.engine.components.screen.list.ItemList;

public class MainHud extends BaseHud
{

	private AbstractImage image;
	private ItemList list;

	@Override
	public String getResource()
	{
		return "json.test";
	}

	@Override
	protected String[] getDependencies()
	{
		return new String[]
		{
			"json.renderer"
		};
	}

	@Override
	protected void onComponentsCreated()
	{
		super.onComponentsCreated();
		
		image = this.<AbstractImage>getComponentByName("test_image_1");
		list = this.<ItemList>getComponentByName("list_test");
	}

	@Override
	protected void onInitialize()
	{
		ArrayList<Integer> data = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			data.add((int) (Math.random() * 0xFFFFFF));
		}

		list.setData(data);
	}
}

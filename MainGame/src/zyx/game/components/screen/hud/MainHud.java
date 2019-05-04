package zyx.game.components.screen.hud;

import java.util.ArrayList;
import zyx.engine.components.screen.image.AbstractImage;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.list.ItemList;
import zyx.game.components.screen.json.JsonSprite;
import zyx.opengl.deferred.DeferredRenderer;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;

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
		image = this.<AbstractImage>getComponentByName("test_image_1");
		list = this.<ItemList>getComponentByName("list_test");
		
		DeferredRenderer renderer = DeferredRenderer.getInstance();
		
		Image debugPos = new Image();
		debugPos.setScale(1, -1);
		debugPos.setPosition(true, 0, 512);
		debugPos.setTexture(new TextureFromInt(512, 512, renderer.positionInt(), TextureSlot.SLOT_0));
		addChild(debugPos);
		
		Image debugNorm = new Image();
		debugNorm.setScale(1, -1);
		debugNorm.setPosition(true, 512, 512);
		debugNorm.setTexture(new TextureFromInt(512, 512, renderer.normalInt(), TextureSlot.SLOT_0));
		addChild(debugNorm);
		
		Image debugCol = new Image();
		debugCol.setScale(1, -1);
		debugCol.setPosition(true, 1024, 512);
		debugCol.setTexture(new TextureFromInt(512, 512, renderer.colorInt(), TextureSlot.SLOT_0));
		addChild(debugCol);
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

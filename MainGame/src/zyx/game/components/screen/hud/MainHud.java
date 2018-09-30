package zyx.game.components.screen.hud;

import zyx.engine.components.screen.AbstractImage;
import zyx.game.components.screen.json.JsonSprite;

public class MainHud extends JsonSprite
{

	private AbstractImage image;
	private float startX;

	@Override
	public String getResource()
	{
		return "json.test";
	}

	@Override
	protected void onInitialized()
	{
		 image = this.<AbstractImage>getComponentByName("test_image_1");
		 
		 startX = image.getX();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (image != null)
		{
//			image.setX(startX + (FloatMath.sin(timestamp / 100f) * 100));
		}
	}

	
}

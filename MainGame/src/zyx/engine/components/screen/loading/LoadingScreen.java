package zyx.engine.components.screen.loading;

import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.game.components.screen.json.JsonSprite;

public class LoadingScreen extends JsonSprite
{

	private Quad progressBar;

	@Override
	public String getResource()
	{
		return "json.loading_screen";
	}

	@Override
	protected void onComponentsCreated()
	{
		progressBar = this.<Quad>getComponentByName("bar");
	}
	
	public void setProgress(float progress)
	{
		if (progressBar != null)
		{
			System.out.println("progress: " + progress);
			if (progress > 0)
			{
				progressBar.visible = true;
				progressBar.setWidth(150 * progress);
			}
			else
			{
				progressBar.visible = false;
			}
		}
	}
}

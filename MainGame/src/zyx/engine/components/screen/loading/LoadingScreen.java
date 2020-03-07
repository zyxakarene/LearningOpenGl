package zyx.engine.components.screen.loading;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.text.Textfield;
import zyx.game.components.screen.json.JsonSprite;

public class LoadingScreen extends JsonSprite
{
	private static final String PERCENT = "%";
	private static final String PERCENT_TEMPLATE = "%.2f%%";

	private DisplayObject progressBar;
	private Textfield PercentText;
	private Textfield descriptionText;
	
	private int oldTextHash;
	private float startWidth;

	@Override
	public String getResource()
	{
		return "json.loading_screen";
	}

	@Override
	protected void onComponentsCreated()
	{
		progressBar = this.<DisplayObject>getComponentByName("progress_bar");
		PercentText = this.<Textfield>getComponentByName("progress_text");
		descriptionText = this.<Textfield>getComponentByName("progress_description");
		startWidth = progressBar.getWidth();
		
		progressBar.visible = false;
	}
	
	public void setProgress(float progress, String message)
	{
		if(PercentText != null)
		{
			String text = String.format(PERCENT_TEMPLATE, progress * 100);
			PercentText.setText(text);
		}
		
		if (descriptionText != null)
		{
			int newHash = message.hashCode();
			if (oldTextHash != newHash)
			{
				oldTextHash = newHash;
				descriptionText.setText(message);
			}
		}
		
		if (progressBar != null)
		{
			if (progress > 0)
			{
				progressBar.visible = true;
				progressBar.setWidth(startWidth * progress);
			}
			else
			{
				progressBar.visible = false;
			}
		}
	}
}

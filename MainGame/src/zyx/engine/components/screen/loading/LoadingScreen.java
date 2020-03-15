package zyx.engine.components.screen.loading;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.text.Textfield;
import zyx.game.components.screen.json.JsonSprite;
import zyx.utils.tween.easing.Easing;

public class LoadingScreen extends JsonSprite
{

	private static final int DOT_STATE_1 = 1;
	private static final int DOT_STATE_2 = 2;
	private static final int DOT_STATE_3 = 3;
	private static final int DOT_STATE_4 = 4;
	private int dotState = DOT_STATE_1;

	private long start = -1;
	private int time = 200;

	private static final String PERCENT_TEMPLATE = "%.2f%%";

	private DisplayObject progressBar;
	private Textfield PercentText;
	private Textfield descriptionText;

	private Image dot1;
	private Image dot2;
	private Image dot3;
	private Image dot4;

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
		dot1 = this.<Image>getComponentByName("loading_dot_1");
		dot2 = this.<Image>getComponentByName("loading_dot_2");
		dot3 = this.<Image>getComponentByName("loading_dot_3");
		dot4 = this.<Image>getComponentByName("loading_dot_4");

		progressBar = this.<DisplayObject>getComponentByName("progress_bar");
		PercentText = this.<Textfield>getComponentByName("progress_text");
		descriptionText = this.<Textfield>getComponentByName("progress_description");
		startWidth = progressBar.getWidth();

		progressBar.visible = false;
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (dot1 != null)
		{
			float size1 = 10f;
			float size2 = 10f;
			float size3 = 10f;
			float size4 = 10f;

			if (start == -1)
			{
				start = timestamp;
			}

			float sizeUp = Easing.LINEAR.in(start, timestamp, 10, 30, time);
			float sizeDown = Easing.LINEAR.in(start, timestamp, 30, 10, time);

			switch (dotState)
			{
				case DOT_STATE_1:
				{
					size1 = sizeUp;
					size4 = sizeDown;
					break;
				}
				case DOT_STATE_2:
				{
					size2 = sizeUp;
					size1 = sizeDown;
					break;
				}
				case DOT_STATE_3:
				{
					size3 = sizeUp;
					size2 = sizeDown;
					break;
				}
				case DOT_STATE_4:
				{
					size4 = sizeUp;
					size3 = sizeDown;
					break;
				}
			}

			if (start + time >= timestamp)
			{
				start = -1;
				dotState++;
				if (dotState > DOT_STATE_4)
				{
					dotState = DOT_STATE_1;
				}
			}

			dot1.setSize(size1, size1);
			dot2.setSize(size2, size2);
			dot3.setSize(size3, size3);
			dot4.setSize(size4, size4);
		}
	}

	public void setProgress(float progress, String message)
	{
		if (PercentText != null)
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

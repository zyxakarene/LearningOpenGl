package zyx.engine.components.screen.interactable;

import zyx.engine.components.screen.image.AbstractImage;
import zyx.engine.components.screen.image.Image;
import zyx.engine.utils.callbacks.ICallback;

public class Checkbox extends Button
{

	private AbstractImage checkImg;
	private boolean checked;
	
	private ICallback<AbstractImage> onCheckImageLoaded;

	public Checkbox(boolean scale9)
	{
		super(scale9);

		onCheckImageLoaded = (AbstractImage data) ->
		{
			onCheckImageLoaded();
		};
		
		checkImg = new Image();
		checkImg.onLoaded.addCallback(onCheckImageLoaded);

		addChild(checkImg);

		checkImg.visible = false;
		checked = false;
	}

	public void loadCheckmark(String resource)
	{
		checkImg.load(resource);
	}

	private void onCheckImageLoaded()
	{
		if (originalWidth != 0)
		{
			checkImg.setWidth(originalWidth);
		}
		
		if (originalHeight != 0)
		{
			checkImg.setHeight(originalHeight);
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();
		checkImg = null;
	}

	@Override
	protected void onMouseClick()
	{
		super.onMouseClick();

		checked = !checked;
		checkImg.visible = checked;
	}

	public boolean isChecked()
	{
		return checked;
	}
}

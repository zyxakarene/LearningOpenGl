package zyx.engine.components.screen;

import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.callbacks.CustomCallback;

public class Button extends InteractableContainer
{

	protected Image upImg;
	protected Image hoverImg;
	protected Image downImg;

	public CustomCallback<InteractableContainer> onButtonClicked;

	public Button(String upTexture, String hoverTexture, String downTexture)
	{
		upImg = new Image();
		hoverImg = new Image();
		downImg = new Image();
		
		upImg.load(upTexture);
		hoverImg.load(hoverTexture);
		downImg.load(downTexture);

		onButtonClicked = new CustomCallback<>();

		addChild(upImg);
		addChild(hoverImg);
		addChild(downImg);

		hoverImg.visible = false;
		downImg.visible = false;
	}

	@Override
	public void dispose()
	{
		super.dispose();

		onButtonClicked.dispose();

		onButtonClicked = null;
		upImg = null;
		hoverImg = null;
		downImg = null;
	}

	@Override
	protected void onMouseEnter()
	{
		CursorManager.getInstance().setCursor(GameCursor.HAND);
		
		downImg.visible = false;
		hoverImg.visible = true;
		upImg.visible = false;
	}

	@Override
	protected void onMouseExit()
	{
		CursorManager.getInstance().setCursor(GameCursor.POINTER);
		
		downImg.visible = false;
		hoverImg.visible = false;
		upImg.visible = true;
	}

	@Override
	protected void onMouseDown()
	{
		downImg.visible = true;
		hoverImg.visible = false;
		upImg.visible = false;
	}

	@Override
	protected void onMouseClick()
	{
		downImg.visible = false;
		hoverImg.visible = true;
		upImg.visible = false;

		if (onButtonClicked.hasEntries())
		{
			ClickDispatcher.getInstance().addClick(onButtonClicked, this);
		}
	}

	@Override
	protected float getQuadWidth()
	{
		return upImg.getWidth();
	}

	@Override
	protected float getQuadHeight()
	{
		return upImg.getHeight();
	}
}

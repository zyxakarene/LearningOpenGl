package zyx.engine.components.screen;

import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.cheats.Print;

public class Button extends InteractableContainer implements ICallback<InteractableContainer>
{

	protected Image upImg;
	protected Image hoverImg;
	protected Image downImg;

	public CustomCallback<InteractableContainer> onButtonClicked;

	public Button(String upTexture, String hoverTexture, String downTexture)
	{
		upImg = new Image(upTexture);
		hoverImg = new Image(hoverTexture);
		downImg = new Image(downTexture);

		onButtonClicked = new CustomCallback<>();
		onButtonClicked.addCallback(this);

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
	public void onCallback(InteractableContainer data)
	{
		position.x += 10;
//		rotation = FloatMath.random() * 360;
	}

	@Override
	protected void onMouseEnter()
	{
		Print.out("onMouseEnter");
		downImg.visible = false;
		hoverImg.visible = true;
		upImg.visible = false;
	}

	@Override
	protected void onMouseExit()
	{
		Print.out("onMouseExit");
		downImg.visible = false;
		hoverImg.visible = false;
		upImg.visible = true;
	}

	@Override
	protected void onMouseDown()
	{
		Print.out("onMouseDown");
		downImg.visible = true;
		hoverImg.visible = false;
		upImg.visible = false;
	}

	@Override
	protected void onMouseClick()
	{
		Print.out("onMouseClick");
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

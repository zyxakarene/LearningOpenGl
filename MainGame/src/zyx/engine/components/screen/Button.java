package zyx.engine.components.screen;

import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.FloatMath;

public class Button extends InteractableContainer implements ICallback<Button>
{

	private Image upImg;
	private Image hoverImg;
	private Image downImg;

	public CustomCallback<Button> onButtonClicked;

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

		upImg = null;
		hoverImg = null;
		downImg = null;
	}

	@Override
	public void onCallback(Button data)
	{
		position.x += 10;
		rotation = FloatMath.random() * 360;
	}

	@Override
	protected void onMouseEnter()
	{
		downImg.visible = false;
		hoverImg.visible = true;
		upImg.visible = false;
	}

	@Override
	protected void onMouseExit()
	{
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
}

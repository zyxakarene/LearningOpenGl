package zyx.engine.components.screen;

import org.lwjgl.util.vector.Vector4f;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.callbacks.CustomCallback;

public class Button extends InteractableContainer
{

	protected Image upImg;
	protected Image hoverImg;
	protected Image downImg;
	
	private Vector4f colors;

	public CustomCallback<InteractableContainer> onButtonClicked;

	public Button(String upTexture, String hoverTexture, String downTexture)
	{
		colors = new Vector4f(1, 1, 1, 1);
		
		upImg = new Image();
		hoverImg = new Image();
		downImg = new Image();
		
		upImg.touchable = false;
		hoverImg.touchable = false;
		downImg.touchable = false;
		
		upImg.load(upTexture);
		hoverImg.load(hoverTexture);
		downImg.load(downTexture);

		onButtonClicked = new CustomCallback<>();

		addChild(upImg);
		addChild(hoverImg);
		addChild(downImg);

		hoverImg.visible = false;
		downImg.visible = false;
		
		buttonMode = true;
		focusable = true;
		hoverIcon = GameCursor.HAND;
	}

	public void setColor(Vector4f color)
	{
		colors.set(color);
		updateMesh();
	}
	
	public void setColor(float r, float g, float b)
	{
		colors.set(r, g, b);
		updateMesh();
	}

	public void setAlpha(float a)
	{
		colors.w = a;
		updateMesh();
	}
	
	private void updateMesh()
	{
		upImg.setColor(colors);
		hoverImg.setColor(colors);
		downImg.setColor(colors);
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
}

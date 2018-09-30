package zyx.engine.components.screen;

import org.lwjgl.util.vector.Vector4f;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.engine.utils.callbacks.ICallback;

public class Button extends InteractableContainer
{

	protected float originalWidth;
	protected float originalHeight;

	protected AbstractImage upImg;
	protected AbstractImage hoverImg;
	protected AbstractImage downImg;

	private Vector4f colors;

	public CustomCallback<InteractableContainer> onButtonClicked;
	
	private boolean loaded;
	private boolean scale9;
	
	private ICallback<AbstractImage> onUpImageLoaded;

	public Button(boolean scale9)
	{
		colors = new Vector4f(1, 1, 1, 1);

		this.scale9 = scale9;
		
		if (scale9)
		{
			upImg = new Scale9Image();
			hoverImg = new Scale9Image();
			downImg = new Scale9Image();
		}
		else
		{
			upImg = new Image();
			hoverImg = new Image();
			downImg = new Image();
		}

		upImg.touchable = false;
		hoverImg.touchable = false;
		downImg.touchable = false;

		onButtonClicked = new CustomCallback<>();

		addChild(upImg);
		addChild(hoverImg);
		addChild(downImg);

		hoverImg.visible = false;
		downImg.visible = false;

		buttonMode = true;
		focusable = true;
		hoverIcon = GameCursor.HAND;
		
		onUpImageLoaded = (AbstractImage data) ->
		{
			onUpImageLoaded();
		};
	}

	public void load(String upResource, String hoverResource, String downResource)
	{
		upImg.load(upResource);
		hoverImg.load(hoverResource);
		downImg.load(downResource);
		
		upImg.onLoaded.addCallback(onUpImageLoaded);
	}

	private void onUpImageLoaded()
	{
		loaded = true;
		
		if (originalWidth != 0)
		{
			setWidth(originalWidth);
		}
		
		if (originalHeight != 0)
		{
			setHeight(originalHeight);
		}
	}
	
	@Override
	public float getWidth()
	{
		if (loaded)
		{
			return super.getWidth();
		}

		return originalWidth;
	}

	@Override
	public float getHeight()
	{
		if (loaded)
		{
			return super.getHeight();
		}

		return originalHeight;
	}

	@Override
	public void setWidth(float value)
	{
		if (loaded)
		{
			upImg.setWidth(value);
			hoverImg.setWidth(value);
			downImg.setWidth(value);
		}
		else
		{
			originalWidth = value;
		}
	}

	@Override
	public void setHeight(float value)
	{
		if (loaded)
		{
			upImg.setHeight(value);
			hoverImg.setHeight(value);
			downImg.setHeight(value);
		}
		else
		{
			originalHeight = value;
		}
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

		onUpImageLoaded = null;
		
		onButtonClicked = null;
		upImg = null;
		hoverImg = null;
		downImg = null;
	}
}

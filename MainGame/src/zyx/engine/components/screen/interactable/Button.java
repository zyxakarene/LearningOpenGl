package zyx.engine.components.screen.interactable;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.events.types.mouse.MouseEvent;
import zyx.engine.components.screen.composed.ComposedButtonColorMap;
import zyx.engine.components.screen.composed.IComposedButton;
import zyx.engine.components.screen.image.AbstractImage;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.callbacks.CustomCallback;

public class Button extends InteractableContainer implements IComposedButton
{

	private ButtonState state;

	protected float originalWidth;
	protected float originalHeight;

	protected AbstractImage[] images;
	protected boolean loaded;
	private int loadedCount;

	private Vector3f[] upColors;
	private Vector3f[] hoverColors;
	private Vector3f[] downColors;
	private float alpha;

	public CustomCallback<InteractableContainer> onButtonClicked;

	private boolean scale9;

	public Button(boolean scale9)
	{
		this.state = ButtonState.UP;
		this.scale9 = scale9;

		alpha = 1;

		onButtonClicked = new CustomCallback<>();

		hoverIcon = GameCursor.HAND;
	}

	@Override
	public void setTextures(String[] textures)
	{
		clean();

		int len = textures.length;
		images = new AbstractImage[len];

		AbstractImage img;
		for (int i = 0; i < len; i++)
		{
			img = scale9 ? new Scale9Image() : new Image();
			images[i] = img;

			img.onLoaded.addCallback(this::onImageLoaded);
			addChild(img);
		}

		String texture;
		for (int i = 0; i < len; i++)
		{
			texture = textures[i];
			img = images[i];
			img.load(texture);
		}
	}

	private void onImageLoaded(AbstractImage img)
	{
		if (loaded == false)
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

		loadedCount++;
		if (loadedCount == images.length)
		{
			if (upColors == null)
			{
				Vector3f white = new Vector3f(1, 1, 1);
				Vector3f[] defaultColor = new Vector3f[loadedCount];
				for (int i = 0; i < loadedCount; i++)
				{
					defaultColor[i] = white;
				}

				setColors(defaultColor, defaultColor, defaultColor);
			}

			updateMesh();
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
	protected void onSetWidth(float value)
	{
		if (loaded)
		{
			for (AbstractImage img : images)
			{
				img.setWidth(value);
			}
		}
		else
		{
			originalWidth = value;
		}
	}

	@Override
	protected void onSetHeight(float value)
	{
		if (loaded)
		{
			for (AbstractImage img : images)
			{
				img.setHeight(value);
			}
		}
		else
		{
			originalHeight = value;
		}
	}

	public void setColors(ComposedButtonColorMap map)
	{
		setColors(map.upColors, map.hoverColors, map.downColors);
	}
	
	@Override
	public void setColors(Vector3f[] up, Vector3f[] hover, Vector3f[] down)
	{
		int newLen = up.length;
		if (upColors == null || upColors.length != newLen)
		{
			upColors = new Vector3f[newLen];
			hoverColors = new Vector3f[newLen];
			downColors = new Vector3f[newLen];
			for (int i = 0; i < newLen; i++)
			{
				upColors[i] = new Vector3f();
				hoverColors[i] = new Vector3f();
				downColors[i] = new Vector3f();
			}
		}

		for (int i = 0; i < newLen; i++)
		{
			upColors[i].set(up[i]);
			hoverColors[i].set(hover[i]);
			downColors[i].set(down[i]);
		}

		updateMesh();
	}

	public void setColor(Vector3f color)
	{
		Vector3f[] singleArray = new Vector3f[]
		{
			color
		};

		setColors(singleArray, singleArray, singleArray);
	}

	public void setColor(float r, float g, float b)
	{
		HELPER_VEC3.set(r, g, b);
		setColor(HELPER_VEC3);
	}

	public void setAlpha(float a)
	{
		alpha = a;
		updateMesh();
	}

	private void updateMesh()
	{
		Vector3f[] colors;
		if (loaded)
		{
			switch (state)
			{
				case HOVER:
					colors = hoverColors;
					break;
				case DOWN:
					colors = downColors;
					break;
				case UP:
				default:
					colors = upColors;
					break;
			}

			int len = images.length;
			AbstractImage img;
			Vector3f col;
			for (int i = 0; i < len; i++)
			{
				col = colors[i];
				img = images[i];
				HELPER_VEC4.set(col.x, col.y, col.z, alpha);

				img.setColor(HELPER_VEC4);
			}
		}
	}

	@Override
	protected void onMouseEnter(MouseEvent event)
	{
		changeStateTo(ButtonState.HOVER);
	}

	@Override
	protected void onMouseExit(MouseEvent event)
	{
		changeStateTo(ButtonState.UP);
	}

	@Override
	protected void onMouseDown(MouseEvent event)
	{
		changeStateTo(ButtonState.DOWN);
	}

	@Override
	protected void onMouseUp(MouseEvent event)
	{
		changeStateTo(ButtonState.UP);
	}
	
	@Override
	protected void onMouseClick(MouseEvent event)
	{
		changeStateTo(ButtonState.HOVER);

		if (onButtonClicked.hasEntries())
		{
			ClickDispatcher.getInstance().addClick(onButtonClicked, this);
		}
	}

	private void changeStateTo(ButtonState newState)
	{
		if (state != newState)
		{
			state = newState;
			updateMesh();
		}
	}

	@Override
	protected void onDispose()
	{
		clean();

		super.onDispose();

		if (onButtonClicked != null)
		{
			onButtonClicked.dispose();
			onButtonClicked = null;
		}
	}

	private void clean()
	{
		loadedCount = 0;

		if (images != null)
		{
			for (AbstractImage img : images)
			{
				img.dispose();
			}
			images = null;
		}
	}
	
	@Override
	public String getDebugIcon()
	{
		return "button.png";
	}
}

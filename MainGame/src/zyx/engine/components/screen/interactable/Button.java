package zyx.engine.components.screen.interactable;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.composed.IComposedButton;
import zyx.engine.components.screen.image.AbstractImage;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.FloatMath;

public class Button extends InteractableContainer implements IComposedButton
{
	private static final int STATE_COUNT = 3;
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

	private ICallback<AbstractImage> onFirstImageLoaded;
	private boolean scale9;

	public Button(boolean scale9)
	{
		this.state = ButtonState.UP;
		this.scale9 = scale9;

		alpha = 1;

		onButtonClicked = new CustomCallback<>();

		buttonMode = true;
		focusable = true;
		hoverIcon = GameCursor.HAND;

		onFirstImageLoaded = (AbstractImage img) -> 
		{
			onImageLoaded(img);
		};
	}

	@Override
	public void setTextures(String[] textures)
	{
		clean();

		int len = textures.length;
		images = new AbstractImage[len];

		AbstractImage img;
		String texture;
		for (int i = 0; i < len; i++)
		{
			texture = textures[i];
			img = scale9 ? new Scale9Image() : new Image();
			images[i] = img;

			img.onLoaded.addCallback(onFirstImageLoaded);
			
			img.load(texture);
			img.touchable = false;
			addChild(img);
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
	public void setWidth(float value)
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
	public void setHeight(float value)
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

			HELPER_VEC4.set(HELPER_VEC3.x, HELPER_VEC3.y, HELPER_VEC3.z, alpha);

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
	protected void onMouseEnter()
	{
		state = ButtonState.HOVER;
		updateMesh();
	}

	@Override
	protected void onMouseExit()
	{
		state = ButtonState.UP;
		updateMesh();
	}

	@Override
	protected void onMouseDown()
	{
		state = ButtonState.DOWN;
		updateMesh();
	}

	@Override
	protected void onMouseClick()
	{
		state = ButtonState.HOVER;
		updateMesh();

		if (onButtonClicked.hasEntries())
		{
			ClickDispatcher.getInstance().addClick(onButtonClicked, this);
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();

		onFirstImageLoaded = null;
		
		if (onButtonClicked != null)
		{
			onButtonClicked.dispose();
			onButtonClicked = null;
		}

		clean();
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
}

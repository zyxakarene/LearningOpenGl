package zyx.engine.components.screen.text;

import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.interactable.InteractableContainer;
import java.awt.event.KeyEvent;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.components.animations.ILoadable;
import zyx.engine.components.screen.base.Quad;
import zyx.engine.curser.GameCursor;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.FontResource;
import zyx.engine.resources.impl.Resource;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.Text;

public class Textfield extends InteractableContainer implements IFocusable, IResourceReady<FontResource>, ILoadable
{

	private Text glText;
	private String text;
	private Vector4f colors;
	private boolean loaded;

	private Resource fontResource;
	private float originalWidth;
	private float originalHeight;

	private boolean hasFocus;
	private int caretPos;
	private Quad caret;

	public Textfield(String text)
	{
		this.text = text;
		this.colors = new Vector4f(1, 1, 1, 1);

		focusable = true;
		hoverIcon = GameCursor.TEXT;
	}

	public Textfield()
	{
		this("");
	}

	@Override
	public void load(String resource)
	{
		fontResource = ResourceManager.getInstance().getResource(resource);
		fontResource.registerAndLoad(this);
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
		if (loaded)
		{
			glText.setColors(colors);
		}
	}

	@Override
	public void onResourceReady(FontResource resource)
	{
		loaded = true;

		BitmapFont font = resource.getContent();
		glText = new Text(font);
		glText.setText(text, colors);

		originalWidth = glText.getWidth();
		originalHeight = glText.getHeight();

		caret = new Quad(1, originalHeight, 0xFF0000);
		caret.visible = false;
		addChild(caret);
	}

	public void setText(String text)
	{
		this.text = text;
		if (loaded)
		{
			glText.setText(text, colors);

			originalWidth = glText.getWidth();
			originalHeight = glText.getHeight();
		}
	}

	public String getText()
	{
		return text;
	}

	@Override
	public float getWidth()
	{
		if (loaded)
		{
			return originalWidth * getScale(true, HELPER_VEC2).x;
		}

		return 0;
	}

	@Override
	public float getHeight()
	{
		if (loaded)
		{
			return originalHeight * getScale(true, HELPER_VEC2).x;
		}

		return 0;
	}

	@Override
	protected void onDraw()
	{
		if (glText != null)
		{
			glText.draw();
		}

		if (hasFocus && caret != null)
		{
			caret.visible = Math.random() > 0.5;
		}

		super.onDraw();
	}

	@Override
	public void setWidth(float value)
	{
		if (loaded)
		{
			getScale(true, HELPER_VEC2);
			setScale(value / originalWidth, HELPER_VEC2.y);
		}
	}

	@Override
	public void setHeight(float value)
	{
		if (loaded)
		{
			getScale(true, HELPER_VEC2);
			setScale(value / originalHeight, HELPER_VEC2.y);
		}
	}

	@Override
	public void dispose()
	{
		if (hasFocus)
		{
			stage.setFocusedObject(null);
		}
		
		super.dispose();

		if (fontResource != null)
		{
			fontResource.unregister(this);
			fontResource = null;
		}

		if (glText != null)
		{
			glText.dispose();
			glText = null;
		}

		if (caret != null)
		{
			caret.dispose();
			caret = null;
		}
	}

	@Override
	protected void onMouseEnter()
	{
	}

	@Override
	protected void onMouseExit()
	{
	}

	@Override
	protected void onMouseDown()
	{
	}

	@Override
	protected void onMouseClick()
	{
		stage.setFocusedObject(this);
	}

	@Override
	public void onKeyPressed(char character)
	{
		if (character == KeyEvent.VK_BACK_SPACE)
		{
			setText(text.substring(0, text.length() - 1));
		}
		else
		{
			setText(text + character);
		}

		caret.setPosition(true, originalWidth, 0);
	}

	@Override
	public void onFocused()
	{
		hasFocus = true;
		caret.visible = true;
		caret.setPosition(true, originalWidth, 0);
	}

	@Override
	public void onUnFocused()
	{
		hasFocus = false;
		caret.visible = false;
	}
}
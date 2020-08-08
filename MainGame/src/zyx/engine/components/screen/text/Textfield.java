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
import zyx.opengl.textures.bitmapfont.alignment.HAlignment;
import zyx.opengl.textures.bitmapfont.alignment.VAlignment;

public class Textfield extends InteractableContainer implements IFocusable, IResourceReady<FontResource>, ILoadable
{
	public static final String DEFAULT_RESOURCE = "font.console";
	
	private Text glText;
	private String text;
	private float fontSize;
	private Vector4f colors;
	private HAlignment hAlign;
	private VAlignment vAlign;
	private boolean loaded;

	private Resource fontResource;
	private float width;
	private float height;

	private boolean hasFocus;
	private Quad caret;

	private Quad[] borders;
	private boolean showBorders;

	public Textfield(String text)
	{
		this.text = text;
		this.colors = new Vector4f(1, 1, 1, 1);

		width = 100;
		height = 100;
		fontSize = 1f;
		hAlign = HAlignment.CENTER;
		vAlign = VAlignment.MIDDLE;
		
		borders = new Quad[4];

		focusable = false;
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
		glText = new Text(font, fontSize, width, height);
		glText.setAlignment(vAlign, hAlign);
		glText.setColors(colors);
		glText.setText(text);

		caret = new Quad(1, height, 0xFF0000);
		caret.visible = false;
		addChild(caret);
	}

	public void setText(String text)
	{
		this.text = text;
		if (loaded)
		{
			glText.setText(text);
		}
	}

	public String getText()
	{
		return text;
	}

	@Override
	public float getWidth()
	{
		return width;
	}

	@Override
	public float getHeight()
	{
		return height;
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
		width = value;
		updateBorders();

		if (loaded)
		{
			glText.setWidth(width);
		}
	}

	@Override
	public void setHeight(float value)
	{
		height = value;
		updateBorders();

		if (loaded)
		{
			glText.setHeight(height);
		}
	}

	public void setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
		updateBorders();

		if (loaded)
		{
			glText.setArea(width, height);
		}
	}

	public void setHAlign(HAlignment alignment)
	{
		hAlign = alignment;
		if (loaded)
		{
			glText.setAlignment(vAlign, hAlign);
		}
	}
	
	public void setVAlign(VAlignment alignment)
	{
		vAlign = alignment;
		if (loaded)
		{
			glText.setAlignment(vAlign, hAlign);
		}
	}
	
	public void setVAlign(String alignment)
	{
		VAlignment align = VAlignment.getFrom(alignment);
		setVAlign(align);
	}

	public void setHAlign(String alignment)
	{
		HAlignment align = HAlignment.getFrom(alignment);
		setHAlign(align);
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

		if (showBorders)
		{
			for (Quad border : borders)
			{
				border.dispose();
			}
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

		caret.setPosition(true, width, 0);
	}

	@Override
	public void onFocused()
	{
		hasFocus = true;
		caret.visible = true;
		caret.setPosition(true, width, 0);
	}

	@Override
	public void onUnFocused()
	{
		hasFocus = false;
		caret.visible = false;
	}

	public void setShowBorders(boolean showBorders)
	{
		this.showBorders = showBorders;
		updateBorders();
	}

	private void updateBorders()
	{
		if (!showBorders)
		{
			return;
		}

		for (Quad border : borders)
		{
			if (border != null)
			{
				border.dispose();
			}
		}

		//Top
		Quad top = new Quad(width, 1, 0xFFFFFF);

		//Bottom
		Quad bottom = new Quad(width, 1, 0xFFFFFF);
		bottom.setY(height);

		//Left
		Quad left = new Quad(1, height, 0xFFFFFF);

		//Right
		Quad right = new Quad(1, height, 0xFFFFFF);
		right.setX(width);

		addChild(top);
		addChild(bottom);
		addChild(left);
		addChild(right);

		borders[0] = top;
		borders[1] = bottom;
		borders[2] = left;
		borders[3] = right;
	}

	public void setFontSize(float fontSize)
	{
		this.fontSize = fontSize;

		if (loaded)
		{
			glText.setFontScale(fontSize);
		}
	}

	@Override
	public String getDebugIcon()
	{
		return "textfield.png";
	}
}

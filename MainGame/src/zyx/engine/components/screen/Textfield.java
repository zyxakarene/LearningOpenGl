package zyx.engine.components.screen;

import org.lwjgl.util.vector.Vector4f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.FontResource;
import zyx.engine.resources.impl.Resource;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.Text;

public class Textfield extends DisplayObject implements IResourceReady<FontResource>
{

	private Text glText;
	private String text;
	private Vector4f colors;
	private boolean loaded;
	
	private Resource resource;

	public Textfield(String font)
	{
		this(font, "");
	}

	public Textfield(String font, String text)
	{
		this.text = text;
		this.colors = new Vector4f(1, 1, 1, 1);
		
		resource = ResourceManager.getInstance().getResource("font." + font);
		resource.registerAndLoad(this);
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
		if(loaded)
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
	}
	
	public void setText(String text)
	{
		this.text = text;
		glText.setText(text, colors);
	}

	public String getText()
	{
		return text;
	}

	@Override
	public float getWidth()
	{
		return 0f;
	}

	@Override
	public float getHeight()
	{
		return 0f;
	}

	@Override
	void onDraw()
	{
		if(glText != null)
		{
			glText.draw();
		}
	}

	@Override
	public void setWidth(float value)
	{
	}

	@Override
	public void setHeight(float value)
	{
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		if(resource != null)
		{
			resource.unregister(this);
			resource = null;
		}
		
		if(glText != null)
		{
			glText.dispose();
			glText = null;
		}
	}
}

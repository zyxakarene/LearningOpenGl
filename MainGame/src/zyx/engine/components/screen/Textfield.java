package zyx.engine.components.screen;

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
	
	private Resource resource;

	public Textfield(String font)
	{
		this(font, "");
	}

	public Textfield(String font, String text)
	{
		this.text = text;
		
		resource = ResourceManager.getInstance().getResource("font." + font);
		resource.registerAndLoad(this);
	}

	@Override
	public void onResourceReady(FontResource resource)
	{
		BitmapFont font = resource.getContent();
		glText = new Text(font);
		glText.setText(text);
	}
	
	public void setText(String text)
	{
		this.text = text;
		glText.setText(text);
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
		transform();
		shader.upload();
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

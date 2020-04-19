package zyx.engine.components.screen.image;

import org.lwjgl.util.vector.Vector4f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.impl.textures.TextureResource;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.textures.AbstractTexture;

public class Image extends AbstractImage implements IResourceReady<TextureResource>
{

	protected static final Vector4f COLORS = SharedShaderObjects.SHARED_VECTOR_4F;

	public Image()
	{
		setColor(1, 1, 1);
	}
	
	@Override
	protected void onTextureResourceReady(AbstractTexture texture)
	{
		if (model != null)
		{
			originalWidth = 0;
			originalHeight = 0;
			
			model.dispose();
			model = null;
		}
		
		model = new ScreenModel(texture, colors);
		model.addVertexData(0, 0, texture);
		model.buildModel();
		
		onModelCreated();
	}
	
	public void setTexture(AbstractTexture texture)
	{
		if (model != null)
		{
			model.dispose();
			model = null;
		}
		
		model = new ScreenModel(texture, colors);
		model.addVertexData(0, 0, texture);
		model.buildModel();
		
		onModelCreated();
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if (loaded)
		{
			model.dispose();
			model = null;
		}
	}

	@Override
	public String getDebugIcon()
	{
		return "image.png";
	}
	
	@Override
	public String toString()
	{
		return String.format("Image{%s}", resource);
	}
}

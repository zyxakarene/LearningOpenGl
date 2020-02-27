package zyx.engine.components.screen.base;

import zyx.engine.components.screen.image.AbstractQuad;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.textures.ColorTexture;
import zyx.utils.Color;

public class Quad extends AbstractQuad
{

	public Quad(float width, float height, int color)
	{
		originalWidth = width;
		originalHeight = height;
		
		Color.toVector(color, colors);
		colors.w = 1;
		
		ColorTexture texture = new ColorTexture(color, width, height);
		model = new ScreenModel(texture, colors);
		model.addVertexData(0, 0, texture);
		model.buildModel();
		
		onModelCreated();
		
		loaded = true;
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		
		if(model != null)
		{
			model.dispose();
			model = null;
		}
	}
	
	@Override
	public String getDebugIcon()
	{
		return "quad.png";
	}
}

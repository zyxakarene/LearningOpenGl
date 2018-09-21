package zyx.engine.components.screen;

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
		
		ColorTexture texture = new ColorTexture(color, width, height);
		model = new ScreenModel(texture, colors);
		model.addVertexData(0, 0, texture);
		model.buildModel();
		
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
	
	
}

package zyx.engine.components.screen.base;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.image.AbstractQuad;
import zyx.opengl.materials.impl.ScreenModelMaterial;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.ColorTexture;
import zyx.utils.Color;

public class Quad extends AbstractQuad
{

	private ScreenModelMaterial material;

	public Quad(float width, float height, int color)
	{
		originalWidth = width;
		originalHeight = height;
		
		Color.toVector(color, colors);
		colors.w = 1;
		
		material = new ScreenModelMaterial(Shader.SCREEN);
		material.setDiffuse(new ColorTexture(color, width, height));
		material.color = new Vector3f(colors);
		material.alpha = 1f;
		
		model = new ScreenModel(material);
		model.addVertexData(0, 0, material.getDiffuse());
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

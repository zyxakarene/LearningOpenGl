package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.textures.ColorTexture;

public class ParticleModel extends AbstractModel
{
	private static final Matrix4f MODEL_MATRIX = ParticleShader.MATRIX_MODEL;

	private ParticleShader shader;

	public ParticleModel()
	{
		super(Shader.PARTICLE);
		this.shader = (ParticleShader) meshShader;

		float[] vertexData =
		{
		   -0.5f,  0.5f, 0, 1, // Top-left
			0.5f,  0.5f, 1, 1, // Top-right
			0.5f, -0.5f, 1, 0, // Bottom-right
		   -0.5f, -0.5f, 0, 0  // Bottom-left
		};
		
		int[] elementData = 
		{
			0, 1, 2,
			2, 3, 0
		};
		
		setVertexData(vertexData, elementData);
		setTexture(new ColorTexture(0xFFFF00));
	}

	@Override
	public void draw()
	{
		shader.bind();
		shader.upload();
		super.draw();
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 4, 0);
		addAttribute("texcoord", 2, 4, 2);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		shader = null;
	}
}

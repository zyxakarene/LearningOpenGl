package zyx.opengl.models.implementations;

import zyx.opengl.GLUtils;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.textures.ColorTexture;
import zyx.opengl.textures.MissingTexture;
import zyx.utils.FloatMath;

public class ParticleModel extends AbstractInstancedModel
{
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
		
		int count = 1000;
		float[] instanceData = new float[count * 4];
		for (int i = 0; i < instanceData.length; i += 4)
		{
			instanceData[i + 0] = FloatMath.random() * 30;
			instanceData[i + 1] = FloatMath.random() * 30;
			instanceData[i + 2] = FloatMath.random() * 1;
			instanceData[i + 3] = FloatMath.random() * 3;
		}
		
		setInstanceData(instanceData, instanceData.length/4);
		setVertexData(vertexData, elementData);
		setTexture(MissingTexture.getInstance());
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
		
		addInstanceAttribute("offset", 3, 4, 0);
		addInstanceAttribute("speed", 1, 4, 3);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		shader = null;
	}
}

package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.utils.FloatMath;

public class ParticleModel extends AbstractInstancedModel
{
	private ParticleShader shader;
	private LoadableParticleVO vo;

	public ParticleModel(LoadableParticleVO vo)
	{
		super(Shader.PARTICLE);

		this.vo = vo;
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
		
		int instanceDataAmount = 9;
		int count = vo.instanceCount;
		float[] instanceData = new float[count * instanceDataAmount];
		for (int i = 0; i < instanceData.length; i += instanceDataAmount)
		{
			for (int j = 0; j < instanceDataAmount; j++)
			{
				instanceData[i + j] = FloatMath.random();
			}
		}
		setInstanceData(instanceData, instanceData.length / instanceDataAmount);
		setVertexData(vertexData, elementData);
		setTexture(vo.gameTexture);
	}

	@Override
	public void draw()
	{
		shader.bind();
		shader.uploadFromVo(vo);
		shader.upload();
		super.draw();
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 4, 0);
		addAttribute("texcoord", 2, 4, 2);
		
		addInstanceAttribute("lifespanRandom", 1, 9, 0);
		addInstanceAttribute("areaRandom", 3, 9, 1);
		addInstanceAttribute("speedRandom", 3, 9, 4);
		addInstanceAttribute("scaleRandom", 1, 9, 7);
		addInstanceAttribute("rotRandom", 1, 9, 8);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		shader = null;
	}
}

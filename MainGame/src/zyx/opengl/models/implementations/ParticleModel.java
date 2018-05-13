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
		
		int count = vo.instanceCount;
		float[] instanceData = new float[count * 4];
		for (int i = 0; i < instanceData.length; i += 4)
		{
			instanceData[i + 0] = FloatMath.random();
			instanceData[i + 1] = FloatMath.random();
			instanceData[i + 2] = FloatMath.random();
			instanceData[i + 3] = FloatMath.random();
		}
		setInstanceData(instanceData, instanceData.length/4);
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
		
		addInstanceAttribute("random", 4, 4, 0);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		shader = null;
	}
}

package zyx.opengl.models.implementations;

import zyx.engine.components.world.WorldObject;
import zyx.opengl.materials.Material;
import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.FloatMath;

public class ParticleModel extends AbstractInstancedModel<ParticleModelMaterial> implements IParticleModel
{

	private static final int[] SHARED_ELEMENT_DATA =
	{
		0, 1, 2,
		2, 3, 0
	};
	
	private ParticleShader shader;
	private LoadableParticleVO vo;

	public ParticleModel(LoadableParticleVO loadableVo)
	{
		super(loadableVo.materialLocal);

		shader = (ParticleShader) meshShader;
		refresh(loadableVo);
	}

	@Override
	public void refresh(LoadableParticleVO loadedVo)
	{
		vo = loadedVo;
		
		defaultMaterial = vo.materialLocal;

		AbstractTexture t = defaultMaterial.getDiffuse();

		float[] vertexData =
		{
			-0.5f, 0.5f, t.x, t.y, // Top-left
			0.5f, 0.5f, t.u, t.y, // Top-right
			0.5f, -0.5f, t.u, t.v, // Bottom-right
			-0.5f, -0.5f, t.x, t.v  // Bottom-left
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
		setVertexData(vertexData, SHARED_ELEMENT_DATA);
	}

	@Override
	public float getRadius()
	{
		return vo.radius;
	}

	@Override
	public void draw(Material material)
	{
		shader.bind();
		shader.uploadFromVo(vo);
		shader.upload();
		super.draw(material);
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

	@Override
	public boolean isWorldParticle()
	{
		return false;
	}

	@Override
	public void setParent(WorldObject parent)
	{
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}

	@Override
	public IParticleModel cloneParticle()
	{
		return new ParticleModel(vo);
	}
}

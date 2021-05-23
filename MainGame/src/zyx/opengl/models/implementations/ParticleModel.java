package zyx.opengl.models.implementations;

import java.util.ArrayList;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.implementations.renderers.ParticleRenderer;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.FloatMath;

public class ParticleModel extends BaseParticleModel
{

	private static final int[] SHARED_ELEMENT_DATA =
	{
		0, 1, 2,
		2, 3, 0
	};
	
	private ParticleShader shader;
	private LoadableParticleVO vo;

	public ParticleModel(LoadableParticleVO vo)
	{
		setSubMeshCount(1);
		
		refresh(vo);
		setup();
	}

	@Override
	public void refresh(LoadableParticleVO loadedVo)
	{
		vo = loadedVo;
		shader = (ParticleShader) loadedVo.materialLocal.shader;
		
		setDefaultMaterials(vo.materialLocal);

		AbstractTexture t = vo.materialLocal.getDiffuse();

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
		
		setInstanceData(0, instanceData, instanceData.length / instanceDataAmount);
		setVertexData(0, vertexData, SHARED_ELEMENT_DATA);
	}

	@Override
	public float getRadius()
	{
		return vo.radius;
	}

	@Override
	public void draw(int index, ParticleModelMaterial material)
	{
		shader.bind();
		shader.uploadFromVo(vo);
		shader.upload();
		super.draw(index, material);
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute(0, "position", 2, 4, 0);
		addAttribute(0, "texcoord", 2, 4, 2);

		addInstanceAttribute(0, "lifespanRandom", 1, 9, 0);
		addInstanceAttribute(0, "areaRandom", 3, 9, 1);
		addInstanceAttribute(0, "speedRandom", 3, 9, 4);
		addInstanceAttribute(0, "scaleRandom", 1, 9, 7);
		addInstanceAttribute(0, "rotRandom", 1, 9, 8);
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
	public void update(long timestamp, int elapsedTime)
	{
	}
	
	@Override
	public ParticleRenderer createRenderer()
	{
		ArrayList<ParticleModelMaterial> materials = getDefaultMaterials();
		ParticleModelMaterial[] array = new ParticleModelMaterial[subMeshCount];
		
		return new ParticleRenderer(this, materials.toArray(array));
	}

	@Override
	public void setParent(WorldObject parent)
	{
	}
}

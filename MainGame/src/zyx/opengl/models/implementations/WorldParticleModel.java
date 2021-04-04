package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.implementations.renderers.WorldParticleRenderer;
import zyx.opengl.shaders.implementations.WorldParticleShader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.DeltaTime;
import zyx.utils.FloatMath;
import zyx.utils.math.MatrixUtils;

public class WorldParticleModel extends BaseParticleModel
{
	private static final int[] SHARED_ELEMENT_DATA =
	{
		0, 1, 2,
		2, 3, 0
	};
	
	private static final int INSTANCE_DATA_COUNT = 22;
	private static final Vector3f PARENT_POS = new Vector3f();
	private static final Matrix4f PARENT_ROTATION = new Matrix4f();
	
	private WorldParticleShader shader;
	private LoadableParticleVO vo;

	private float[] instanceData;
	private int[] particleAge;
	private WorldObject parent;
	
	public WorldParticleModel(LoadableParticleVO loadableVo)
	{
		super(loadableVo.materialWorld);

		shader = (WorldParticleShader) meshShader;
		refresh(loadableVo);
	}

	@Override
	public void refresh(LoadableParticleVO loadedVo)
	{
		vo = loadedVo;
		defaultMaterial = vo.materialWorld;
		
		AbstractTexture t = defaultMaterial.getDiffuse();
		
		float[] vertexData =
		{
		   -0.5f,  0.5f, t.x, t.y, // Top-left
			0.5f,  0.5f, t.u, t.y, // Top-right
			0.5f, -0.5f, t.u, t.v, // Bottom-right
		   -0.5f, -0.5f, t.x, t.v  // Bottom-left
		};
		
		int count = vo.instanceCount;
		
		particleAge = new int[vo.instanceCount];
		instanceData = new float[count * INSTANCE_DATA_COUNT];
		for (int i = 0; i < particleAge.length; i++)
		{
			particleAge[i] = (int) (i * -(vo.lifespan / vo.instanceCount) + vo.lifespan);
		}

		setInstanceData(instanceData, instanceData.length / INSTANCE_DATA_COUNT);
		setVertexData(vertexData, SHARED_ELEMENT_DATA);
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		boolean preloaded = false;
		
		for (int i = 0; i < particleAge.length; i++)
		{
			int age = particleAge[i];
			if (age >= vo.lifespan)
			{
				if(!preloaded)
				{
					preloaded = true;
					loadParentData();
				}
				
				age = (int) (age - vo.lifespan);
				resetParticle(i);
			}		
			
			particleAge[i] = age + elapsedTime;
		}
		
		if (preloaded)
		{
			setInstanceData(instanceData, instanceData.length / INSTANCE_DATA_COUNT);
		}
	}
	
	private void resetParticle(int particleID)
	{
		int dataIndex = particleID * INSTANCE_DATA_COUNT;
		instanceData[dataIndex + 0] = PARENT_POS.x;
		instanceData[dataIndex + 1] = PARENT_POS.y;
		instanceData[dataIndex + 2] = PARENT_POS.z;
		
		instanceData[dataIndex + 3] = PARENT_ROTATION.m00;
		instanceData[dataIndex + 4] = PARENT_ROTATION.m01;
		instanceData[dataIndex + 5] = PARENT_ROTATION.m02;
		
		instanceData[dataIndex + 6] = PARENT_ROTATION.m10;
		instanceData[dataIndex + 7] = PARENT_ROTATION.m11;
		instanceData[dataIndex + 8] = PARENT_ROTATION.m12;
		
		instanceData[dataIndex + 9] = PARENT_ROTATION.m20;
		instanceData[dataIndex + 10] = PARENT_ROTATION.m21;
		instanceData[dataIndex + 11] = PARENT_ROTATION.m22;
		
		instanceData[dataIndex + 12] = DeltaTime.getTimestamp();
		instanceData[dataIndex + 13] = vo.lifespan;
		
		instanceData[dataIndex + 14] = FloatMath.random(); //Speed random x
		instanceData[dataIndex + 15] = FloatMath.random(); //Speed random y
		instanceData[dataIndex + 16] = FloatMath.random(); //Speed random z
		instanceData[dataIndex + 17] = FloatMath.random(); //Area random x
		instanceData[dataIndex + 18] = FloatMath.random(); //Area random y
		instanceData[dataIndex + 19] = FloatMath.random(); //Area random z
		instanceData[dataIndex + 20] = FloatMath.random(); //Scale random
		instanceData[dataIndex + 21] = FloatMath.random(); //Rotation random
	}
	
	@Override
	public float getRadius()
	{
		return vo.radius;
	}
	
	@Override
	public void draw(ParticleModelMaterial material)
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
		
		addInstanceAttribute("worldPos",	3, INSTANCE_DATA_COUNT, 0);
		addInstanceAttribute("worldRot_0",	3, INSTANCE_DATA_COUNT, 3);
		addInstanceAttribute("worldRot_1",	3, INSTANCE_DATA_COUNT, 6);
		addInstanceAttribute("worldRot_2",	3, INSTANCE_DATA_COUNT, 9);
		addInstanceAttribute("spawnTime",	1, INSTANCE_DATA_COUNT, 12);
		addInstanceAttribute("lifespan",	1, INSTANCE_DATA_COUNT, 13);
		addInstanceAttribute("speedRandom",	3, INSTANCE_DATA_COUNT, 16);
		addInstanceAttribute("areaRandom",	3, INSTANCE_DATA_COUNT, 19);
		addInstanceAttribute("scaleRandom",	1, INSTANCE_DATA_COUNT, 20);
		addInstanceAttribute("rotRandom",	1, INSTANCE_DATA_COUNT, 21);
	}
	
	@Override
	public void dispose()
	{
		super.dispose();

		shader = null;
	}

	@Override
	public void setParent(WorldObject parent)
	{
		this.parent = parent;
	}

	private void loadParentData()
	{
		parent.getPosition(false, PARENT_POS);
		MatrixUtils.RemoveScaleFrom(parent.worldMatrix(), PARENT_ROTATION);
	}

	@Override
	public boolean isWorldParticle()
	{
		return true;
	}

	@Override
	public WorldParticleRenderer createRenderer()
	{
		WorldParticleModel clone = new WorldParticleModel(vo);
		return new WorldParticleRenderer(clone, defaultMaterial);
	}
}

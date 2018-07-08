package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldParticleShader;
import zyx.utils.FloatMath;

public class WorldParticleModel extends AbstractInstancedModel implements IParticleModel
{
	private static final int INSTANCE_DATA_COUNT = 19;
	private static final Vector3f PARENT_POS = new Vector3f();
	private static final Matrix4f PARENT_ROTATION = new Matrix4f();
	
	private WorldParticleShader shader;
	private LoadableParticleVO vo;
	private WorldObject parent;

	private float[] instanceData;
	private int[] particleAge;
	
	public WorldParticleModel(LoadableParticleVO vo)
	{
		super(Shader.WORLD_PARTICLE);

		this.vo = vo;
		this.shader = (WorldParticleShader) meshShader;

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
		
		particleAge = new int[vo.instanceCount];
		instanceData = new float[count * INSTANCE_DATA_COUNT];
		for (int i = 0; i < particleAge.length; i++)
		{
			particleAge[i] = (int) (i * -(vo.lifespan / vo.instanceCount) + vo.lifespan);
		}

		setInstanceData(instanceData, instanceData.length / INSTANCE_DATA_COUNT);
		setVertexData(vertexData, elementData);
		setTexture(vo.gameTexture);
	}

	@Override
	public IParticleModel cloneParticle()
	{
		return new WorldParticleModel(vo);
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
		
		instanceData[dataIndex + 12] = WorldParticleShader.elapsedTime;
		instanceData[dataIndex + 13] = vo.lifespan;
		
		instanceData[dataIndex + 14] = FloatMath.random();
		instanceData[dataIndex + 15] = FloatMath.random();
		instanceData[dataIndex + 16] = FloatMath.random();
		instanceData[dataIndex + 17] = FloatMath.random();
		instanceData[dataIndex + 18] = FloatMath.random();
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
		
		addInstanceAttribute("worldPos", 3, 19, 0);
		addInstanceAttribute("worldRot_0", 3, 19, 3);
		addInstanceAttribute("worldRot_1", 3, 19, 6);
		addInstanceAttribute("worldRot_2", 3, 19, 9);
		addInstanceAttribute("spawnTime", 1, 19, 12);
		addInstanceAttribute("lifespan", 1, 19, 13);
		addInstanceAttribute("speedRandom", 3, 19, 16);
		addInstanceAttribute("scaleRandom", 1, 19, 17);
		addInstanceAttribute("rotRandom", 1, 19, 18);
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
		PARENT_ROTATION.load(parent.worldMatrix());
	}

	@Override
	public boolean isWorldParticle()
	{
		return true;
	}
}

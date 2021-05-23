package zyx.opengl.models.implementations;

import java.util.ArrayList;
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
		setSubMeshCount(1);
		
		setDefaultMaterials(loadableVo.materialWorld);
		createObjects();
		refresh(loadableVo);
		setupAttributes();
	}

	@Override
	public void refresh(LoadableParticleVO loadedVo)
	{
		vo = loadedVo;
		shader = (WorldParticleShader) loadedVo.materialWorld.shader;

		AbstractTexture t = vo.materialWorld.getDiffuse();
		
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

		setInstanceData(0, instanceData, instanceData.length / INSTANCE_DATA_COUNT);
		setVertexData(0, vertexData, SHARED_ELEMENT_DATA);
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
			setInstanceData(0, instanceData, instanceData.length / INSTANCE_DATA_COUNT);
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
		
		addInstanceAttribute(0, "worldPos",	3, INSTANCE_DATA_COUNT, 0);
		addInstanceAttribute(0, "worldRot_0",	3, INSTANCE_DATA_COUNT, 3);
		addInstanceAttribute(0, "worldRot_1",	3, INSTANCE_DATA_COUNT, 6);
		addInstanceAttribute(0, "worldRot_2",	3, INSTANCE_DATA_COUNT, 9);
		addInstanceAttribute(0, "spawnTime",	1, INSTANCE_DATA_COUNT, 12);
		addInstanceAttribute(0, "lifespan",	1, INSTANCE_DATA_COUNT, 13);
		addInstanceAttribute(0, "speedRandom",	3, INSTANCE_DATA_COUNT, 16);
		addInstanceAttribute(0, "areaRandom",	3, INSTANCE_DATA_COUNT, 19);
		addInstanceAttribute(0, "scaleRandom",	1, INSTANCE_DATA_COUNT, 20);
		addInstanceAttribute(0, "rotRandom",	1, INSTANCE_DATA_COUNT, 21);
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
		ArrayList<ParticleModelMaterial> materials = getDefaultMaterials();
		ParticleModelMaterial[] array = new ParticleModelMaterial[subMeshCount];
		
		WorldParticleModel clone = new WorldParticleModel(vo);
		return new WorldParticleRenderer(clone, materials.toArray(array));
	}
}

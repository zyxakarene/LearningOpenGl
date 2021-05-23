package zyx.engine.components.meshbatch;

import java.util.ArrayList;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.meshes.MeshBatchResource;
import zyx.opengl.models.implementations.MeshBatchModel;
import zyx.opengl.models.implementations.renderers.MeshBatchRenderer;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

class MeshBatch<E extends MeshBatchEntity> implements IResourceReady<MeshBatchResource>, IUpdateable, IDisposeable
{

	private MeshBatchResource meshResource;
	private MeshBatchRenderer renderer;
	
	private ArrayList<E> entities;
	private int entityCount;
	private float[] batchData;

	MeshBatch(String resource)
	{
		meshResource = ResourceManager.getInstance().getResourceAs(resource);
		meshResource.registerAndLoad(this);
		
		entityCount = 0;
		entities = new ArrayList<>();
		batchData = new float[entityCount];
	}

	@Override
	public void onResourceReady(MeshBatchResource resource)
	{
		renderer = resource.getContent();
	}

	public void addEntity(E entity)
	{
		entities.add(entity);
		entityCount = entities.size();
		batchData = new float[entityCount * MeshBatchModel.INSTANCE_DATA_AMOUNT];
	}

	public void removeEntity(E entity)
	{
		entities.remove(entity);
		entityCount = entities.size();
		batchData = new float[entityCount * MeshBatchModel.INSTANCE_DATA_AMOUNT];
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		final int offset = MeshBatchModel.INSTANCE_DATA_AMOUNT;
		E entity;
		Vector3f pos;
		Quaternion rot;
		for (int i = 0; i < entityCount; i++)
		{
			entity = entities.get(i);
			entity.update(timestamp, elapsedTime);
			
			pos = entity.position;
			rot = entity.rotation;
			
			int entryOffset = offset * i;
			batchData[entryOffset + 0] = pos.x;
			batchData[entryOffset + 1] = pos.y;
			batchData[entryOffset + 2] = pos.z;
			batchData[entryOffset + 3] = rot.x;
			batchData[entryOffset + 4] = rot.y;
			batchData[entryOffset + 5] = rot.z;
			batchData[entryOffset + 6] = rot.w;
			batchData[entryOffset + 7] = entity.scale;
			batchData[entryOffset + 8] = entity.cubemapIndex / 255f;
		}
		
		if (renderer != null)
		{
			renderer.setMeshBatchData(0, batchData); //TODO Correct index
		}
	}

	public boolean isEmpty()
	{
		return entityCount <= 0;
	}
	
	@Override
	public void dispose()
	{
		if (meshResource != null)
		{
			meshResource.unregister(this);
			meshResource = null;
		}
		
		if (entities != null)
		{
			entities.clear();
			entities = null;
		}
		
		if (renderer != null)
		{
			renderer.dispose();
			renderer = null;
		}
		
		entityCount = 0;
		entities = null;
		batchData = null;
	}
}

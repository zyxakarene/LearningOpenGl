package zyx.engine.components.meshbatch;

import java.util.ArrayList;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.meshes.MeshBatchResource;
import zyx.opengl.models.implementations.MeshBatchModel;
import zyx.opengl.models.implementations.renderers.wrappers.MeshBatchModelWrapper;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

class MeshBatch<E extends MeshBatchEntity> implements IResourceReady<MeshBatchResource>, IUpdateable, IDisposeable
{
	private static final int MAX_ENTITIES = 3000;
	
	private MeshBatchResource meshResource;
	private MeshBatchModelWrapper wrapper;
	
	private ArrayList<E> entitiesToAdd;
	private ArrayList<E> entitiesToRemove;
	
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
		
		entitiesToAdd = new ArrayList<>();
		entitiesToRemove = new ArrayList<>();
	}
	
	public boolean canAddMore()
	{
		return entities.size() + entitiesToAdd.size() < MAX_ENTITIES;
	}

	@Override
	public void onResourceReady(MeshBatchResource resource)
	{
		wrapper = resource.getContent();
	}

	public void addEntity(E entity)
	{
		if (entitiesToAdd.contains(entity) == false)
		{
			entitiesToAdd.add(entity);
		}
	}

	public void removeEntity(E entity)
	{
		if (entitiesToRemove.contains(entity) == false)
		{
			entitiesToRemove.add(entity);
		}
		
		entitiesToAdd.remove(entity);
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		addAndRemoveEntities();
		
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
		
		if (wrapper != null)
		{
			wrapper.setMeshBatchData(batchData);
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
		
		if (wrapper != null)
		{
			wrapper.dispose();
			wrapper = null;
		}
		
		if (entitiesToRemove != null)
		{
			entitiesToRemove.clear();
			entitiesToRemove = null;
		}
		
		if (entitiesToAdd != null)
		{
			entitiesToAdd.clear();
			entitiesToAdd = null;
		}
		
		entityCount = 0;
		entities = null;
		batchData = null;
	}

	private void addAndRemoveEntities()
	{
		boolean removedAny = entities.removeAll(entitiesToRemove);
		boolean addedAny = entities.addAll(entitiesToAdd);
		
		if (removedAny || addedAny)
		{
			entitiesToRemove.clear();
			entitiesToAdd.clear();
			
			entityCount = entities.size();
			batchData = new float[entityCount * MeshBatchModel.INSTANCE_DATA_AMOUNT];
		}
	}

	boolean contains(E entity)
	{
		return entitiesToAdd.contains(entity) || entities.contains(entity);
	}
}

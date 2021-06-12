package zyx.engine.components.meshbatch;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.opengl.GLUtils;
import zyx.utils.interfaces.IUpdateable;

public class MeshBatchManager implements IUpdateable
{
	private static final MeshBatchManager INSTANCE = new MeshBatchManager();

	public static MeshBatchManager getInstance()
	{
		return INSTANCE;
	}

	private ArrayList<MeshBatch> meshBatchesList;
	private HashMap<String, MeshBatch> meshBatchesMap;
	
	private MeshBatchManager()
	{
		meshBatchesList = new ArrayList<>();
		meshBatchesMap = new HashMap<>();
	}
	
	public void addEntity(MeshBatchEntity entity)
	{
		String view = entity.viewId;
		MeshBatch batch = meshBatchesMap.get(view);
	
		if (batch == null)
		{
			batch = new MeshBatch(view);
			meshBatchesMap.put(view, batch);
			meshBatchesList.add(batch);
		}
		
		batch.addEntity(entity);
	}
	
	public void removeEntity(MeshBatchEntity entity)
	{
		String view = entity.viewId;
		MeshBatch batch = meshBatchesMap.get(view);
	
		if (batch != null)
		{
			batch.removeEntity(entity);
			
			
			if (batch.isEmpty())
			{
				meshBatchesList.remove(batch);
				meshBatchesMap.remove(view);
				
				batch.dispose();
			}
		}
	}

	public void clean()
	{
		for (MeshBatch batch : meshBatchesList)
		{
			batch.dispose();
		}
		
		meshBatchesList.clear();
		meshBatchesMap.clear();
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (MeshBatch meshBatch : meshBatchesList)
		{
			meshBatch.update(timestamp, elapsedTime);
		}
	}
}

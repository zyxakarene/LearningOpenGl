package zyx.engine.components.meshbatch;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.utils.interfaces.IUpdateable;

public class MeshBatchManager implements IUpdateable
{
	private static final MeshBatchManager INSTANCE = new MeshBatchManager();

	public static MeshBatchManager getInstance()
	{
		return INSTANCE;
	}

	private ArrayList<MeshBatch> meshBatchesList;
	private HashMap<String, ArrayList<MeshBatch>> meshBatchesMap;
	
	private MeshBatchManager()
	{
		meshBatchesList = new ArrayList<>();
		meshBatchesMap = new HashMap<>();
	}
	
	public void addEntity(MeshBatchEntity entity)
	{
		String view = entity.viewId;
		ArrayList<MeshBatch> batchList = meshBatchesMap.get(view);
		if (batchList == null)
		{
			batchList = new ArrayList<>();
			meshBatchesMap.put(view, batchList);
		}
	
		MeshBatch batch = null;
		if (!batchList.isEmpty())
		{
			int listLen = batchList.size();
			for (int i = 0; i < listLen; i++)
			{
				MeshBatch batchCheck = batchList.get(i);
				if (batchCheck.canAddMore())
				{	
					batch = batchCheck;
					break;
				}
			}
		}
		
		if (batch == null)
		{
			batch = new MeshBatch(view);
			batchList.add(batch);
			meshBatchesList.add(batch);
		}
		
		batch.addEntity(entity);
	}
	
	public void removeEntity(MeshBatchEntity entity)
	{
		String view = entity.viewId;
		ArrayList<MeshBatch> batchList = meshBatchesMap.get(view);
	
		if (batchList != null)
		{
			int listLen = batchList.size();
			for (int i = 0; i < listLen; i++)
			{
				MeshBatch batch = batchList.get(i);
				if (batch.contains(entity))
				{
					batch.removeEntity(entity);
					
					if (batch.isEmpty())
					{
						batchList.remove(i);
						
						meshBatchesList.remove(batch);
						batch.dispose();
					}
				}
			}
			
			if (batchList.isEmpty())
			{
				meshBatchesMap.remove(view);
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

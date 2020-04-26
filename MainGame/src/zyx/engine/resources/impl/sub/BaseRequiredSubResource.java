package zyx.engine.resources.impl.sub;

import java.util.LinkedList;
import zyx.engine.resources.impl.ExternalResource;
import zyx.engine.utils.callbacks.ICallback;

public abstract class BaseRequiredSubResource extends ExternalResource implements ICallback<SubResourceBatch>
{

	private int batchesLoaded;
	private LinkedList<SubResourceBatch> allBatches;

	public BaseRequiredSubResource(String path)
	{
		super(path);
	
		batchesLoaded = 0;
		allBatches = new LinkedList<>();
	}

	protected void addResourceBatch(SubResourceBatch... batches)
	{
		for (SubResourceBatch batch : batches)
		{
			allBatches.add(batch);
		}
		
		for (SubResourceBatch batch : batches)
		{
			batch.load(this);
		}
	}

	@Override
	public void onCallback(SubResourceBatch data)
	{
		batchesLoaded++;
		if (batchesLoaded == allBatches.size())
		{
			batchesLoaded = 0;
			onSubBatchesLoaded();
		}
	}

	protected void cancelSubBatches()
	{
		while (allBatches.isEmpty() == false)
		{				
			SubResourceBatch batch = allBatches.remove();
			batch.dispose();
		}
	}
		
	@Override
	protected void onDispose()
	{
		super.onDispose();

		while (allBatches.isEmpty() == false)
		{				
			SubResourceBatch batch = allBatches.remove();
			batch.dispose();
		}

		allBatches.clear();
		batchesLoaded = 0;
	}

	protected abstract void onSubBatchesLoaded();
	
}

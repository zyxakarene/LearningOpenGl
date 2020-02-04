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
			batch.load(this);
		}
	}

	@Override
	public void onCallback(SubResourceBatch data)
	{
		batchesLoaded++;
		if (batchesLoaded == allBatches.size())
		{
			onSubBatchesLoaded();
		}
	}
		
	@Override
	protected void onDispose()
	{
		super.onDispose();

		if (allBatches != null)
		{
			SubResourceBatch batch;
			while (allBatches.isEmpty() == false)
			{				
				batch = allBatches.remove();
				batch.dispose();
			}
			
			allBatches.clear();
			allBatches = null;
		}
	}

	protected abstract void onSubBatchesLoaded();
	
}

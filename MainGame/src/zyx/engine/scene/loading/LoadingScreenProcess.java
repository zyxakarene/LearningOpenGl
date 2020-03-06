package zyx.engine.scene.loading;

import zyx.game.controls.process.BaseProcess;

public abstract class LoadingScreenProcess extends BaseProcess
{
	private int currentSum;
	
	private LoadingScreenProcessQueue loadingQueue;
	
	public abstract int getTotalProgress();
	
	void setQueue(LoadingScreenProcessQueue queue)
	{
		loadingQueue = queue;
	}
	
	protected void addDone(int count)
	{
		currentSum += count;
		
		int total = getTotalProgress();
		if (currentSum >= total)
		{
			int origValue = currentSum - count;
			count = total - origValue;
		}
		loadingQueue.addProgression(count);
	}
}

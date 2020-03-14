package zyx.engine.scene.loading;

import zyx.game.controls.process.BaseProcess;

public abstract class LoadingScreenProcess extends BaseProcess
{
	private int currentSum;
	
	LoadingScreenProcessQueue loadingQueue;
	String taskDescription;

	public LoadingScreenProcess(String taskDescription)
	{
		this.taskDescription = taskDescription;
	}

	public abstract int getTotalProgress();
	
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

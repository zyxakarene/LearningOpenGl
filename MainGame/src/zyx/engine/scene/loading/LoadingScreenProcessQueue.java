package zyx.engine.scene.loading;

import zyx.game.controls.process.ProcessQueue;

public class LoadingScreenProcessQueue extends ProcessQueue<LoadingScreenProcess>
{
	private float totalSumCompleted;
	private float totalSumRequired;
	private float ratioCompleted;

	public LoadingScreenProcessQueue()
	{
	}
	
	@Override
	public void addProcess(LoadingScreenProcess process)
	{
		super.addProcess(process);
		process.loadingQueue = this;
		
		totalSumRequired += process.getTotalProgress();
	}

	void addProgression(int count)
	{
		totalSumCompleted += count;
		ratioCompleted = totalSumCompleted / totalSumRequired;
	}

	float getRatioCompleted()
	{
		return ratioCompleted;
	}

	String getCurrentTaskDescription()
	{
		if (currentProcess != null)
		{
			return currentProcess.taskDescription;
		}
		
		return "";
	}
}

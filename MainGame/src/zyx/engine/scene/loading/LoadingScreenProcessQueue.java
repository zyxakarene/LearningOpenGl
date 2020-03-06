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
		process.setQueue(this);
		
		totalSumRequired += process.getTotalProgress();
		System.out.println("required now " + totalSumRequired);
	}

	void addProgression(int count)
	{
		totalSumCompleted += count;
		System.out.println("Completed now: " + totalSumCompleted);
		ratioCompleted = totalSumCompleted / totalSumRequired;
	}

	public float getRatioCompleted()
	{
		return ratioCompleted;
	}
}

package zyx.engine.scene.loading;

public class WaitingProcess extends LoadingScreenProcess
{

	private int sum;
	private int count;

	public WaitingProcess(int sum)
	{
		this.sum = sum;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		count++;
		addDone(1);
		
		if (count >= sum)
		{
			finish();
		}
	}

	@Override
	public int getTotalProgress()
	{
		return sum;
	}

}

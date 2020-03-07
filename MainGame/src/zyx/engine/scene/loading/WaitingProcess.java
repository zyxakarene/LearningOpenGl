package zyx.engine.scene.loading;

public class WaitingProcess extends LoadingScreenProcess
{

	private int sum;
	private int count;

	public WaitingProcess(int sum, String text)
	{
		super(text);
		this.sum = sum;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (count < sum)
		{
			count++;
			addDone(1);

			if (count >= sum)
			{
				onDoneWaiting();
			}
		}
	}

	@Override
	public int getTotalProgress()
	{
		return sum;
	}

	protected void onDoneWaiting()
	{
		finish();
	}
}

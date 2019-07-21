package zyx.synchronizer;

abstract class AbstractRunner<E, R> implements Runnable
{

	protected BaseExchange<E, R> exchange;
	
	private boolean enabled;

	public AbstractRunner()
	{
	}

	void setExchange(BaseExchange<E, R> exchange)
	{
		this.exchange = exchange;
		this.enabled = true;
	}

	@Override
	public final void run()
	{
		while (enabled)
		{
			performAction();
		}
	}

	protected abstract void performAction();
	
	protected abstract String getName();
	
	protected void dispose()
	{
		enabled = false;
		exchange = null;
	}
}

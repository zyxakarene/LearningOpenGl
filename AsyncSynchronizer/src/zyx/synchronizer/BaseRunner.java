package zyx.synchronizer;

public abstract class BaseRunner<E, R> extends AbstractRunner<E, R>
{
	@Override
	protected void performAction()
	{
		E entry = exchange.getEntry();
		if (entry != null)
		{
			R reply = handleEntry(entry);

			if (reply != null)
			{
				exchange.addReply(reply);
			}
		}
		else
		{
			exchange.sleep();
		}
	}

	protected abstract R handleEntry(E entry);
}

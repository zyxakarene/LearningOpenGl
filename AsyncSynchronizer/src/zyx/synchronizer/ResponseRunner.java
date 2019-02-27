package zyx.synchronizer;

public abstract class ResponseRunner<E, R> extends AbstractRunner<E, R>
{

	@Override
	protected void performAction()
	{
		R reply = getReply();
		if (reply != null)
		{
			exchange.addReply(reply);
		}
	}
	
	protected abstract R getReply();
}

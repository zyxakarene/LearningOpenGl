package zyx.synchronizer;

import java.util.ArrayList;

public abstract class BaseAsyncSynchronizer<E, R>
{

	private BaseExchange<E, R> exchange;
	private ArrayList<AbstractRunner<E, R>> runners;

	public BaseAsyncSynchronizer()
	{
		runners = new ArrayList<>();
	}

	protected void setExchange(BaseExchange<E, R> exchange)
	{
		this.exchange = exchange;
	}
	
	protected void addRunner(BaseRunner<E, R> runner)
	{
		runner.setExchange(exchange);
		
		Thread thread = new Thread(runner);
		thread.setName(runner.getName() + "#" + (runners.size() + 1));
		thread.setDaemon(true);

		runners.add(runner);

		thread.start();
	}
	
	protected void addResponseRunner(ResponseRunner<E, R> runner)
	{
		runner.setExchange(exchange);
		
		Thread thread = new Thread(runner);
		thread.setName(runner.getName() + "#" + (runners.size() + 1));
		thread.setDaemon(true);

		runners.add(runner);

		thread.start();
	}

	public void handleReplies()
	{
		exchange.sendReplies();
	}

	public void addEntry(E entry)
	{
		exchange.addEntry(entry);
	}

	public void cancelEntry(E entry)
	{
		exchange.removeEntry(entry);
	}

	public void dispose()
	{
		for (AbstractRunner runner : runners)
		{
			runner.dispose();
		}

		runners.clear();

		exchange.dispose();
	}

	public abstract void addThreads(int count);
}

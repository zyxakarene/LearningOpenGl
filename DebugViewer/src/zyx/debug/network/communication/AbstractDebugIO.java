package zyx.debug.network.communication;

import java.io.IOException;

abstract class AbstractDebugIO implements Runnable
{

	private boolean active;

	AbstractDebugIO()
	{
		active = true;
	}

	@Override
	public final void run()
	{
		while (active)
		{
			onRun();

			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException ex)
			{
			}
		}
	}

	protected abstract void onRun();

	protected void fail()
	{
		System.out.println("Connection failed, killing " + this);
		
		active = false;

		synchronized (DebugServer.SERVER_LOCK)
		{
			DebugServer.SERVER_LOCK.notifyAll();
		}
	}
	
	protected void fail(IOException ex)
	{
		ex.printStackTrace();
		System.out.println("Connection failed from exception! Killing " + this);
		
		active = false;

		synchronized (DebugServer.SERVER_LOCK)
		{
			DebugServer.SERVER_LOCK.notifyAll();
		}
	}

	boolean isActive()
	{
		return active;
	}

}

package zyx.debug.views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import zyx.debug.DebugController;

public class DebugWindowAdaptor extends WindowAdapter implements Runnable
{

	private Thread updater;
	private boolean active;

	private DebugView view;

	public DebugWindowAdaptor(DebugView view)
	{
		this.view = view;
		active = true;

		updater = new Thread(this);
		updater.setDaemon(true);
		updater.setPriority(2);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		active = false;
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		view.onWindowOpened();
		
		updater.start();
	}

	@Override
	public void run()
	{
//		try
//		{
//			Thread.sleep(1000);
//		}
//		catch (InterruptedException ex)
//		{
//		}
		
		while (active)
		{
			view.update();
			synchronized (DebugController.SHARED_LOCK)
			{

				try
				{
					DebugController.SHARED_LOCK.wait();
				}
				catch (InterruptedException ex)
				{
				}
			}
		}
	}
}

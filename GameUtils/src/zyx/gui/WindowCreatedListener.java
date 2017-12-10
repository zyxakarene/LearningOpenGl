package zyx.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowCreatedListener extends WindowAdapter
{

	private final IWindowOpened listener;

	public WindowCreatedListener(IWindowOpened listener)
	{
		this.listener = listener;
	}
	
	@Override
	public void windowOpened(WindowEvent e)
	{
		listener.windowOpened();
	}

	static interface IWindowOpened
	{
		void windowOpened();
	}
}

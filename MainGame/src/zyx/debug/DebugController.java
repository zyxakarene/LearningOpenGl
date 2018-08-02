package zyx.debug;

import zyx.debug.views.DebugView;

public class DebugController
{
	public static final Object SHARED_LOCK = new Object();
	
	public static void show()
	{
		java.awt.EventQueue.invokeLater(() ->
		{
			new DebugView().setVisible(true);
		});
	}
}

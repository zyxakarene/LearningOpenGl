package zyx.debug;

import zyx.debug.views.DebugView;

public class DebugController
{
	public static final Object SHARED_LOCK = new Object();
	
	private static DebugView view;
	
	public static void show()
	{
		if (view == null)
		{
			java.awt.EventQueue.invokeLater(() ->
			{
				view = new DebugView();
				view.setVisible(true);
			});
		}
	}

	public static void close()
	{
		if(view != null)
		{
			view.dispose();
			view = null;
		}
	}
}

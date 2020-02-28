package zyx.debug.views.network;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NetworkListAdaptor extends MouseAdapter
{

	private INetworkListClicked callback;
	
	public NetworkListAdaptor(INetworkListClicked callback)
	{
		this.callback = callback;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() >= 2)
		{
			callback.onListClicked();
		}
	}

}

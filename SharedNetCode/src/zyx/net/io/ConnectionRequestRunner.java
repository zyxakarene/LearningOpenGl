package zyx.net.io;

import java.io.IOException;
import java.net.DatagramPacket;
import zyx.net.io.requests.ConnectionRequest;

class ConnectionRequestRunner implements Runnable, IDisposeable
{
	private boolean enabled;

	public ConnectionRequestRunner()
	{
		enabled = true;
	}

	@Override
	public void run()
	{
		while (enabled)
		{
			ConnectionRequest request = ConnectionExchange.getRequest();
			if (request != null)
			{
				byte[] byteData = request.getData();
				DatagramPacket packet = new DatagramPacket(byteData, byteData.length, request.host, request.port);
				
				try
				{
					PersistentConnection.getInstance().send(packet);
				}
				catch (IOException ex)
				{
					enabled = false;
					System.out.println("Error at sending data: " + ex);
				}
			}
			else
			{
				ConnectionExchange.sleepRequest();
			}
		}
	}

	@Override
	public void dispose()
	{
		enabled = false;
	}

}

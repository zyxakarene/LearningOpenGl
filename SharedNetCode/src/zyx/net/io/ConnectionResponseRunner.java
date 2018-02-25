package zyx.net.io;

import java.io.IOException;
import java.net.DatagramPacket;
import zyx.net.io.requests.ConnectionResponse;

class ConnectionResponseRunner implements Runnable, IDisposeable
{

	private boolean enabled = true;

	@Override
	public void run()
	{
		while (enabled)
		{
			try
			{
				DatagramPacket packet = PersistentConnection.getInstance().receive();

				ConnectionResponse response = new ConnectionResponse(packet);
				ConnectionExchange.addResponse(response);
			}
			catch (IOException ex)
			{
				enabled = false;
				System.out.println("Error at receiving data: " + ex);
			}
		}
	}

	@Override
	public void dispose()
	{
		enabled = false;
	}
}

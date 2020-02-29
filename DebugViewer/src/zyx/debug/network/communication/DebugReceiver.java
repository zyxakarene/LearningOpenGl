package zyx.debug.network.communication;

import java.io.IOException;
import java.io.ObjectInputStream;

public class DebugReceiver implements Runnable
{
	private ObjectInputStream in;

	public DebugReceiver(ObjectInputStream in)
	{
		this.in = in;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				byte dataType = in.readByte();
			}
			catch (IOException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}

}

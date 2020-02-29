package zyx.debug.network.communication;

import java.io.ObjectOutputStream;

public class DebugSender implements Runnable
{
	private ObjectOutputStream out;

	public DebugSender(ObjectOutputStream out)
	{
		this.out = out;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			
		}
	}

}

package zyx.debug.network.communication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import zyx.debug.DebugController;
import zyx.debug.network.vo.pools.PoolInformation;

public class DebugReceiver extends AbstractDebugIO
{
	private static final short POOL_TYPE = 0;

	private DataInputStream in;

	public DebugReceiver(DataInputStream in)
	{
		this.in = in;
	}

	@Override
	protected void onRun()
	{
		try
		{
			short dataType = in.readShort();
			int len = in.readInt();
			byte[] data = new byte[len];
			in.readFully(data);
			
			if (dataType == POOL_TYPE)
			{
				PoolInformation.fromData(data);
			}
			
			synchronized (DebugController.SHARED_LOCK)
			{
				DebugController.SHARED_LOCK.notifyAll();
			}
		}
		catch (IOException ex)
		{
			fail(ex);
		}
	}
}

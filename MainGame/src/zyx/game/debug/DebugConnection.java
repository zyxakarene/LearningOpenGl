package zyx.game.debug;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import zyx.game.debug.io.SingleWriteFileOutputStream;
import zyx.game.debug.pools.DebugPoolList;
import zyx.game.debug.pools.PoolInfo;
import zyx.utils.cheats.Print;

public class DebugConnection implements Runnable
{

	private static final short POOL_TYPE = 0;

	private boolean active;

	private DataOutputStream output;

	public DebugConnection()
	{
		active = true;
	}

	@Override
	public void run()
	{
		createConnection();

		while (active)
		{
			if (DebugPoolList.hasChanges())
			{
				sendPoolInfo();
			}

			sleep(100);
		}
	}

	private void sendPoolInfo()
	{
		ArrayList<PoolInfo> changes = DebugPoolList.getPoolChanges();

		try
		{
			SingleWriteFileOutputStream outStream = new SingleWriteFileOutputStream();
			outStream.writeByte(changes.size());
			for (PoolInfo change : changes)
			{
				outStream.writeInt(change.id);
				outStream.writeInt(change.free);
				outStream.writeInt(change.taken);
				outStream.writeInt(change.total);
			}
			
			byte[] data = outStream.getData();
			sendDataAs(data, POOL_TYPE);
		}
		catch (IOException ex)
		{
			Print.err("Could not send pool information:", ex.getMessage());
			active = false;
		}
	}

	private void sendDataAs(byte[] data, short type) throws IOException
	{
		System.out.println("Send " + data.length + " bytes of data");
		
		output.writeShort(type);
		output.writeInt(data.length);
		output.write(data);
		output.flush();
	}
	
	private void createConnection()
	{
		try
		{
			Socket socket = new Socket("localhost", 4444);
			Print.out("Successfully connected to the remote debugger!");
			OutputStream out = socket.getOutputStream();

			output = new DataOutputStream(out);
		}
		catch (IOException ex)
		{
			Print.err("Could not connect to remote debugger:", ex.getMessage());
			active = false;
		}
	}
	
	private void sleep(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException ex)
		{
		}
	}
}

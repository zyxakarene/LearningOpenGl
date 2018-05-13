package zyx.game.controls.resourceloader.requests.vo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import zyx.utils.exceptions.Msg;

public class ResourceDataInputStream extends DataInputStream
{

	private byte[] bytes;
	
	public ResourceDataInputStream(byte[] bytes)
	{
		super(new ByteArrayInputStream(bytes));
		
		this.bytes = bytes;
	}

	public int getLength()
	{
		return bytes.length;
	}

	public byte[] getData()
	{
		return bytes;
	}
	
	@Override
	public synchronized void reset()
	{
		try
		{
			super.reset();
		}
		catch (IOException ex)
		{
			//This should never happen
			Msg.error("IOException where it should never happen??", ex);
		}
	}
}

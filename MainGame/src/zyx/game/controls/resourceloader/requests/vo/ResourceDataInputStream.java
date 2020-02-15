package zyx.game.controls.resourceloader.requests.vo;

import java.io.DataInputStream;
import java.io.IOException;
import zyx.utils.exceptions.Msg;
import zyx.utils.interfaces.IDisposeable;

public class ResourceDataInputStream extends DataInputStream implements IDisposeable
{

	private ResourceByteArray source;
	
	private ResourceDataInputStream(ResourceByteArray source)
	{
		super(source);
		
		this.source = source;
	}
	
	public ResourceDataInputStream(byte[] data)
	{
		this(new ResourceByteArray(data));
	}
	
	public int getPosition()
	{
		int length = source.length();
		int remaining = source.available();
		
		return length - remaining;
	}

	public int getLength()
	{
		return source.length();
	}

	public byte[] getData()
	{
		return source.getData();
	}
	
	public ResourceDataInputStream createClone()
	{
		return new ResourceDataInputStream(source.getData());
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

	@Override
	public void dispose()
	{
		if (source != null)
		{
			source.dispose();
			source = null;
		}
	}
}

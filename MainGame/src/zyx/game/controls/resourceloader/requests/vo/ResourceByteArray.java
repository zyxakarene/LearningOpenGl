package zyx.game.controls.resourceloader.requests.vo;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import zyx.utils.interfaces.IDisposeable;

public class ResourceByteArray extends ByteArrayInputStream implements IDisposeable
{

	public ResourceByteArray(byte[] buf)
	{
		super(buf);
	}

	@Override
	public int read(byte[] b)
	{
		return read(b, 0, b.length);
	}
	
	public byte[] getData()
	{
		return buf;
	}
	
	public int length()
	{
		return count;
	}
	
	@Override
	public String toString()
	{
		return Arrays.toString(buf);
	}

	@Override
	public void dispose()
	{
		buf = null;
	}
	
	
}

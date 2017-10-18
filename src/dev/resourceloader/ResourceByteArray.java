package dev.resourceloader;

import java.io.ByteArrayInputStream;

public class ResourceByteArray extends ByteArrayInputStream
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
	
	public int length()
	{
		return count;
	}	
}

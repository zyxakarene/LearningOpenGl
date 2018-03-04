package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class AbstractSerializer
{

	AbstractSerializer()
	{
	}
	
	public final void write(Object obj, String name, ObjectOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeShort(getType());
		
		onWrite(obj, out);
	}
	
	public final int getLength(String name, Object obj)
	{
		return (Character.BYTES * name.length()) + getDataLength(obj);
	}
	
	public final int getLength(Object obj)
	{
		return getDataLength(obj);
	}
	
	protected abstract void onWrite(Object obj, ObjectOutputStream out) throws IOException;
	protected abstract short getType();
	protected abstract int getDataLength(Object obj);
	
	public abstract Object read(ObjectInputStream stream) throws IOException;
}

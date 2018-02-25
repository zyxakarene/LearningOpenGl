package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class StringSerializer extends AbstractSerializer
{

	StringSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		String value = (String) obj;
		out.writeUTF(value);
	}
	
	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readUTF();
	}

	@Override
	public int getDataLength(Object obj)
	{
		String value = (String) obj;
		return Short.BYTES + (Character.BYTES * value.length());
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_STRING;
	}

	
}

package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class ShortSerializer extends AbstractSerializer
{

	ShortSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		short value = (short) obj;
		out.writeShort(value);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readShort();
	}

	@Override
	public int getDataLength(Object obj)
	{
		return Short.BYTES;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_SHORT;
	}
}

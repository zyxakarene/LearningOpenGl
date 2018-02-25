package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class DoubleSerializer extends AbstractSerializer
{

	DoubleSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		double value = (double) obj;
		out.writeDouble(value);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readDouble();
	}

	@Override
	public int getDataLength(Object obj)
	{
		return Double.BYTES;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_DOUBLE;
	}
}

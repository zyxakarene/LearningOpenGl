package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class FloatSerializer extends AbstractSerializer
{

	FloatSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		float value = (float) obj;
		out.writeFloat(value);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readFloat();
	}

	@Override
	public int getDataLength(Object obj)
	{
		return Float.BYTES;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_FLOAT;
	}
}

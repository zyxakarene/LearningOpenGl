package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class BooleanSerializer extends AbstractSerializer
{

	BooleanSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		boolean value = (boolean) obj;
		out.writeBoolean(value);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readBoolean();
	}

	@Override
	public int getDataLength(Object obj)
	{
		return 1;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_BOOLEAN;
	}
}

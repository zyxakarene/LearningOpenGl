package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class CharacterSerializer extends AbstractSerializer
{

	CharacterSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		char value = (char) obj;
		out.writeChar(value);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readChar();
	}

	@Override
	public int getDataLength(Object obj)
	{
		return Character.BYTES;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_CHARACTER;
	}
}

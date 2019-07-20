package zyx.net.data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import zyx.net.writers.AbstractSerializer;
import zyx.net.writers.Serializers;

class DataObjectDeserializer
{

	private static final Serializers SERIALIZERS = Serializers.getInstance();

	static void deserializeToMap(byte[] inputData, HashMap<String, Object> outputMap) throws IOException
	{
		ByteArrayInputStream in = new ByteArrayInputStream(inputData);
		try (ObjectInputStream stream = new ObjectInputStream(in))
		{
			int length = stream.readShort();

			for (int i = 0; i < length; i++)
			{
				String name = stream.readUTF();
				short type = stream.readShort();

				AbstractSerializer serializer = SERIALIZERS.getFromType(type);

				Object data = serializer.read(stream);
				outputMap.put(name, data);
			}
		}
	}

	static <T> void deserializeToArray(byte[] inputData, ArrayList<T> list) throws IOException
	{
		ByteArrayInputStream in = new ByteArrayInputStream(inputData);
		try (ObjectInputStream stream = new ObjectInputStream(in))
		{
			int length = stream.readShort();

			for (int i = 0; i < length; i++)
			{
				String unusedName = stream.readUTF();
				short type = stream.readShort();

				AbstractSerializer serializer = SERIALIZERS.getFromType(type);

				T data = (T) serializer.read(stream);
				list.add(data);
			}

			stream.close();
		}
	}
}

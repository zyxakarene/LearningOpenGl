package zyx.net.data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import zyx.net.writers.AbstractSerializer;
import zyx.net.writers.Serializers;

class DataObjectDeserializer
{

	private ObjectInputStream stream;

	DataObjectDeserializer(byte[] data) throws IOException
	{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		stream = new ObjectInputStream(in);
	}

	void deserializeMap(HashMap<String, Object> map) throws IOException
	{
		Serializers serializers = Serializers.getInstance();
		int length = stream.readShort();
		
		for (int i = 0; i < length; i++)
		{
			String name = stream.readUTF();
			short type = stream.readShort();
			
			AbstractSerializer serializer = serializers.getFromType(type);
			
			Object data = serializer.read(stream);
			map.put(name, data);
		}
		
		stream.close();
	}

	void deserializeArray(ArrayList<Object> list) throws IOException
	{
		Serializers serializers = Serializers.getInstance();
		int length = stream.readShort();
		
		for (int i = 0; i < length; i++)
		{
			String name = stream.readUTF();
			short type = stream.readShort();
			
			AbstractSerializer serializer = serializers.getFromType(type);
			
			Object data = serializer.read(stream);
			list.add(data);
		}
		
		stream.close();
	}
}

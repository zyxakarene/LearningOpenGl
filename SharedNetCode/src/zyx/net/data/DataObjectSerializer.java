package zyx.net.data;

import java.io.*;
import zyx.net.writers.AbstractSerializer;

class DataObjectSerializer
{

	private ObjectOutputStream stream;
	private ByteArrayOutputStream out;

	public DataObjectSerializer(int size) throws IOException
	{
		out = new ByteArrayOutputStream(size);
		stream = new ObjectOutputStream(out);
	}

	byte[] toByteArray(DataEntry[] entries) throws IOException
	{
		Object data;
		AbstractSerializer writer;
		String name;

		stream.writeShort(entries.length);
		
		for (DataEntry entry : entries)
		{
			data = entry.data;
			writer = entry.writer;
			name = entry.name;

			writer.write(data, name, stream);
		}
		
		stream.flush();
		
		byte[] result = out.toByteArray();
		stream.close();
		
		return result;
	}
}

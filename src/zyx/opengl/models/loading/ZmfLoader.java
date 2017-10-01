package zyx.opengl.models.loading;

import java.io.*;
import java.util.Arrays;
import zyx.opengl.models.implementations.WorldModel;

public class ZmfLoader
{

	public static WorldModel loadFromZmf(String name)
	{
		long start = System.currentTimeMillis();
		try
		{
			File input = new File("assets/models/" + name);

			RandomAccessFile raf = new RandomAccessFile(input, "r");
			byte[] buffer = new byte[(int) raf.length()];
			raf.read(buffer, 0, buffer.length);
			
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(buffer));
			
			int verticies = in.readInt();
			int elements = in.readInt();

			float vertexData[] = new float[verticies];
			int elementData[] = new int[elements];

			for (int i = 0; i < verticies; i++)
			{
				vertexData[i] = in.readFloat();
			}
			for (int i = 0; i < elements; i++)
			{
				elementData[i] = in.readShort();
			}

			WorldModel result = new WorldModel(vertexData, elementData);
			
			long end = System.currentTimeMillis();
			System.out.println("Took " + (end - start) + "ms to load " + name);

			return result;

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}

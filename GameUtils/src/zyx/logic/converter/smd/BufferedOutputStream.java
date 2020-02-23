package zyx.logic.converter.smd;

import java.io.OutputStream;
import java.util.ArrayList;

class BufferedOutputStream extends OutputStream
{
	private ArrayList<Integer> buffer;

	BufferedOutputStream()
	{
		buffer = new ArrayList<>();
	}
	
	@Override
	public void write(int b)
	{
		buffer.add(b);
	}

	byte[] getData()
	{
		byte[] data = new byte[buffer.size()];
		for (int i = 0; i < data.length; i++)
		{
			byte b = buffer.get(i).byteValue();
			data[i] = b;
		}
		
		return data;
	}

}

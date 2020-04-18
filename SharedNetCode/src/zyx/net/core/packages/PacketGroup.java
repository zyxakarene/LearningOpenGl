package zyx.net.core.packages;

class PacketGroup
{

	private byte[][] dataSplits;
	private int combinedLength;

	PacketGroup()
	{
	}

	void addData(short index, short length, byte[] data)
	{
		if (dataSplits == null)
		{
			dataSplits = new byte[length][];
		}
		
		if (dataSplits[index] != null)
		{
			throw new RuntimeException("Duplicate index data");
		}
		
		combinedLength += data.length;
		dataSplits[index] = data;
	}

	boolean isComplete()
	{
		for (byte[] split : dataSplits)
		{
			if (split == null)
			{
				return false;
			}
		}
		
		return true;
	}

	byte[] getCombinedData()
	{
		byte[] combinedData = new byte[combinedLength];
		int dest = 0;
		for (byte[] split : dataSplits)
		{
			System.arraycopy(split, 0, combinedData, dest, split.length);
			dest += split.length;
		}
		
		return combinedData;
	}

	void dispose()
	{
		if (dataSplits != null)
		{
			for (int i = 0; i < dataSplits.length; i++)
			{
				dataSplits[i] = null;				
			}
			dataSplits = null;
		}
	}

}

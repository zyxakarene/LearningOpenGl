package zyx.net.core.packages;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class PacketSplitter
{

	static final int MAX_LENGTH = 512;
	static final int HEADER_LENGTH = Short.BYTES * 3; //Id, Part, Toal

	private static short ID_COUNTER = 0;

	public static DatagramPacket[] splitDataToPackets(byte[] rawData, InetAddress host, int port)
	{
		short id = ID_COUNTER++;

		int len = rawData.length;
		if (len <= MAX_LENGTH)
		{
			byte[] data = new byte[HEADER_LENGTH + len];
			addHeaderData(data, id, (short) 0, (short) 1);
			
			System.arraycopy(rawData, 0, data, HEADER_LENGTH, rawData.length);
			return new DatagramPacket[]
			{
				new DatagramPacket(data, data.length, host, port)
			};
		}

		short splits = (short) Math.ceil(len / MAX_LENGTH);
		DatagramPacket[] packets = new DatagramPacket[splits];
		for (short i = 0; i < splits; i++)
		{
			byte[] splitData;
			if (i == splits - 1)
			{
				//Last part
				int splitLen = len - (MAX_LENGTH * (splits - 1));
				splitData = new byte[splitLen + HEADER_LENGTH];
				
				System.arraycopy(rawData, len - splitLen, splitData, HEADER_LENGTH, splitLen);
			}
			else
			{
				//First parts
				splitData = new byte[MAX_LENGTH + HEADER_LENGTH];
				System.arraycopy(rawData, i * MAX_LENGTH, splitData, HEADER_LENGTH, MAX_LENGTH);
			}
			
			addHeaderData(splitData, id, i, splits);
			packets[i] = new DatagramPacket(splitData, splitData.length, host, port);
		}

		return packets;
	}

	private static void addHeaderData(byte[] data, short id, short index, short length)
	{
		data[0] = (byte) ((id >>> 8) & 0xFF);
		data[1] = (byte) (id & 0xFF);
		
		data[2] = (byte) ((index >>> 8) & 0xFF);
		data[3] = (byte) (index & 0xFF);
		
		data[4] = (byte) ((length >>> 8) & 0xFF);
		data[5] = (byte) (length & 0xFF);
	}
}

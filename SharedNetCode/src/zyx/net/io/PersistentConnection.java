package zyx.net.io;

import java.io.IOException;
import java.net.*;

class PersistentConnection
{
	static final int BUFFER_SIZE = 512;
	
	private static PersistentConnection INSTANCE = new PersistentConnection();

	private DatagramSocket socket;
	
	private PersistentConnection()
	{
		
	}
	
	void connectTo(InetAddress address, int port) throws SocketException
	{
		socket = new DatagramSocket();
		socket.connect(address, port);
	}
	
	void listenTo(int port) throws SocketException
	{
		socket = new DatagramSocket(port);
	}
	
	void send(DatagramPacket data) throws IOException
	{
		if (data.getLength() > BUFFER_SIZE)
		{
			throw new IllegalArgumentException("Packet size too big! " + data.getLength());
		}
		
		socket.send(data);
	}
	
	DatagramPacket receive() throws IOException
	{
		byte[] buffer = new byte[BUFFER_SIZE];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		socket.receive(packet);
		
		return packet;
	}
	
	static PersistentConnection getInstance()
	{
		return INSTANCE;
	}
}

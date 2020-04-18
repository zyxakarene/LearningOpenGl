package zyx.net.io.connections;

import java.net.InetAddress;

public class ConnectionResponseData
{

	InetAddress senderHost;
	int senderPort;
	public byte[] byteData;

	public ConnectionResponseData(byte[] data, InetAddress senderHost, int senderPort)
	{
		this.byteData = data;
		this.senderHost = senderHost;
		this.senderPort = senderPort;
	}
}

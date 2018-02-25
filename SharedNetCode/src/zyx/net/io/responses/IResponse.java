package zyx.net.io.responses;

import java.net.InetAddress;
import zyx.net.data.ReadableDataObject;

public interface IResponse
{
	public void onMessage(ReadableDataObject data, InetAddress senderHost, int senderPort);
}

package zyx.net.io.requests;

import java.net.InetAddress;
import zyx.net.core.ConnectionHandler;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.connections.ConnectionRequest;

public abstract class ParamNetworkRequestOne<T1> extends BaseNetworkRequest
{

	public ParamNetworkRequestOne(String command)
	{
		super(command);
	}

	@Override
	protected final void getDataObject(WriteableDataObject data, Object[] params)
	{
		getDataObject(data, (T1) params[0]);
	}
	
	protected abstract void getDataObject(WriteableDataObject data, T1 param1);
}

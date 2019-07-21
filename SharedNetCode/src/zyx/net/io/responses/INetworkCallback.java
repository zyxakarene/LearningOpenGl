package zyx.net.io.responses;

public interface INetworkCallback<T>
{
	public void onNetworkResponse(T data);
}

package udp.controller;

import auth.LoginResponse;
import auth.PlayerJoinedResponse;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;

public class ServerNetworkController extends BaseNetworkController
{

	public ServerNetworkController()
	{
		super(new ServerNetworkCallbacks());
	}

	@Override
	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new LoginResponse());
		dispatcher.addResponseCallback(new PlayerJoinedResponse());
	}

}

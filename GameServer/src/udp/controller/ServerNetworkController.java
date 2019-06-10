package udp.controller;

import auth.LoginResponse;
import auth.PlayerJoinedRequest;
import auth.PlayerJoinedResponse;
import pos.PlayerPosRequest;
import pos.PlayerPosResponse;
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
		dispatcher.addRequestHandler(new PlayerJoinedRequest());
		dispatcher.addRequestHandler(new PlayerPosRequest());
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new LoginResponse());
		dispatcher.addResponseCallback(new PlayerJoinedResponse());
		dispatcher.addResponseCallback(new PlayerPosResponse());
	}

}

package zyx.server.controller;

import zyx.game.joining.PlayerJoinGameRequest;
import zyx.game.joining.PlayerJoinGameResponse;
import zyx.game.login.AuthenticateRequest;
import zyx.game.login.AuthenticateResponse;
import zyx.game.login.LoginRequest;
import zyx.game.login.LoginResponse;
import zyx.game.position.PlayerPosRequest;
import zyx.game.position.PlayerPosResponse;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;
import zyx.server.requests.SetupGameRequest;

public class ServerNetworkController extends BaseNetworkController
{

	public ServerNetworkController()
	{
		super(new ServerNetworkCallbacks());
	}

	@Override
	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
		dispatcher.addRequestHandler(new PlayerJoinGameRequest());
		dispatcher.addRequestHandler(new PlayerPosRequest());
		dispatcher.addRequestHandler(new LoginRequest());
		dispatcher.addRequestHandler(new SetupGameRequest());
		dispatcher.addRequestHandler(new AuthenticateRequest());
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new LoginResponse());
		dispatcher.addResponseCallback(new PlayerJoinGameResponse());
		dispatcher.addResponseCallback(new PlayerPosResponse());
		dispatcher.addResponseCallback(new AuthenticateResponse());
	}

}

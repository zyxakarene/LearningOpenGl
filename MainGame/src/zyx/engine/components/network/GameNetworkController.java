package zyx.engine.components.network;

import zyx.game.network.connection.JoinGameRequest;
import zyx.game.network.connection.login.LoginResponse;
import zyx.game.network.connection.login.LoginRequest;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;

public class GameNetworkController extends BaseNetworkController
{

	public GameNetworkController()
	{
		super(new GameNetworkCallbacks());
	}

	@Override
	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
		dispatcher.addRequestHandler(new LoginRequest());
		dispatcher.addRequestHandler(new JoinGameRequest());
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new LoginResponse());
	}

}

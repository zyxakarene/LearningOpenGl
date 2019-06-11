package zyx.engine.components.network;

import zyx.game.network.connection.JoinGameRequest;
import zyx.game.network.connection.PlayerJoinedResponse;
import zyx.game.network.connection.login.LoginResponse;
import zyx.game.network.connection.login.LoginRequest;
import zyx.game.position.PlayerPosRequest;
import zyx.game.position.PlayerPosResponse;
import zyx.game.scene.PlayerHandler;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;

public class GameNetworkController extends BaseNetworkController
{

	public GameNetworkController(PlayerHandler playerHandler)
	{
		super(new GameNetworkCallbacks(playerHandler));
	}

	@Override
	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
		dispatcher.addRequestHandler(new LoginRequest());
		dispatcher.addRequestHandler(new JoinGameRequest());
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

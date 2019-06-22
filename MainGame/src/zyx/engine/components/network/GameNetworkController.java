package zyx.engine.components.network;

import zyx.game.joining.PlayerJoinGameRequest;
import zyx.game.joining.PlayerJoinGameResponse;
import zyx.game.login.AuthenticateRequest;
import zyx.game.login.AuthenticateResponse;
import zyx.game.login.LoginRequest;
import zyx.game.login.LoginResponse;
import zyx.game.network.joining.SetupGameResponse;
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
		dispatcher.addRequestHandler(new AuthenticateRequest());
		dispatcher.addRequestHandler(new PlayerJoinGameRequest());
		dispatcher.addRequestHandler(new PlayerPosRequest());
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new LoginResponse());
		dispatcher.addResponseCallback(new AuthenticateResponse());
		dispatcher.addResponseCallback(new SetupGameResponse());
		dispatcher.addResponseCallback(new PlayerJoinGameResponse());
		dispatcher.addResponseCallback(new PlayerPosResponse());
	}

}

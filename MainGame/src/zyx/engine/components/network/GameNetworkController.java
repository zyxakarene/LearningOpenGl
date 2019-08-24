package zyx.engine.components.network;

import zyx.game.network.connection.CharacterJoinGameResponse;
import zyx.game.joining.CharacterLeaveGameRequest;
import zyx.game.joining.CharacterLeaveGameResponse;
import zyx.game.login.AuthenticateResponse;
import zyx.game.login.LoginRequest;
import zyx.game.login.LoginResponse;
import zyx.game.joining.SetupGameResponse;
import zyx.game.ping.PingRequest;
import zyx.game.ping.PingResponse;
import zyx.game.position.PlayerMassPositionsResponse;
import zyx.game.position.PlayerPosRequest;
import zyx.game.scene.CharacterHandler;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;

public class GameNetworkController extends BaseNetworkController
{

	public GameNetworkController(CharacterHandler playerHandler)
	{
		addCallbackMap(new PingPongNetworkCallbacks());
		addCallbackMap(new GameNetworkCallbacks(playerHandler));
	}

	@Override
	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
		dispatcher.addRequestHandler(new LoginRequest());
		dispatcher.addRequestHandler(new CharacterLeaveGameRequest());
		dispatcher.addRequestHandler(new PlayerPosRequest());
		dispatcher.addRequestHandler(new PingRequest());
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new LoginResponse());
		dispatcher.addResponseCallback(new AuthenticateResponse());
		dispatcher.addResponseCallback(new SetupGameResponse());
		dispatcher.addResponseCallback(new CharacterJoinGameResponse());
		dispatcher.addResponseCallback(new CharacterLeaveGameResponse());
		dispatcher.addResponseCallback(new PlayerMassPositionsResponse());
		dispatcher.addResponseCallback(new PingResponse());
	}

}

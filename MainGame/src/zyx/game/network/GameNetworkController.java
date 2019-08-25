package zyx.game.network;

import zyx.engine.components.network.PingPongNetworkCallbacks;
import zyx.game.network.callbacks.ItemNetworkCallbacks;
import zyx.game.network.callbacks.CharacterNetworkCallbacks;
import zyx.game.joining.CharacterJoinGameResponse;
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
import zyx.game.scene.ItemHolderHandler;
import zyx.game.scene.ItemHandler;
import zyx.game.world.items.*;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;

public class GameNetworkController extends BaseNetworkController
{

	public GameNetworkController(ItemHolderHandler playerHandler, ItemHandler itemHandler)
	{
		addCallbackMap(new PingPongNetworkCallbacks());
		addCallbackMap(new CharacterNetworkCallbacks(playerHandler));
		addCallbackMap(new ItemNetworkCallbacks(itemHandler));
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
		
		dispatcher.addResponseCallback(new ItemCreateBillResponse());
		dispatcher.addResponseCallback(new ItemCreateFoodResponse());
		dispatcher.addResponseCallback(new ItemCreateOrdersResponse());
		dispatcher.addResponseCallback(new ItemAddOrderResponse());
		dispatcher.addResponseCallback(new ItemDestroyResponse());
		dispatcher.addResponseCallback(new ItemSetOwnerResponse());
		dispatcher.addResponseCallback(new ItemSetTypeResponse());
	}

}

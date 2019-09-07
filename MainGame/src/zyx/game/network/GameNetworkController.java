package zyx.game.network;

import zyx.game.world.guests.GuestGiveOrdersResponse;
import zyx.engine.components.network.PingPongNetworkCallbacks;
import zyx.game.network.callbacks.ItemNetworkCallbacks;
import zyx.game.network.callbacks.GameNetworkCallbacks;
import zyx.game.joining.CharacterJoinGameResponse;
import zyx.game.joining.CharacterLeaveGameRequest;
import zyx.game.joining.CharacterLeaveGameResponse;
import zyx.game.login.AuthenticateResponse;
import zyx.game.login.LoginRequest;
import zyx.game.login.LoginResponse;
import zyx.game.joining.SetupGameResponse;
import zyx.game.ping.PingRequest;
import zyx.game.ping.PingResponse;
import zyx.game.position.CharacterMassPositionsResponse;
import zyx.game.position.CharacterPosRequest;
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
		addCallbackMap(new GameNetworkCallbacks(playerHandler, itemHandler));
		addCallbackMap(new ItemNetworkCallbacks(itemHandler));
		addCallbackMap(new PingPongNetworkCallbacks());
	}

	@Override
	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
		dispatcher.addRequestHandler(new CharacterLeaveGameRequest());
		dispatcher.addRequestHandler(new CharacterPosRequest());
		dispatcher.addRequestHandler(new LoginRequest());
		dispatcher.addRequestHandler(new PingRequest());
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new CharacterMassPositionsResponse());
		dispatcher.addResponseCallback(new CharacterLeaveGameResponse());
		dispatcher.addResponseCallback(new CharacterJoinGameResponse());
		dispatcher.addResponseCallback(new AuthenticateResponse());
		dispatcher.addResponseCallback(new SetupGameResponse());
		dispatcher.addResponseCallback(new LoginResponse());
		dispatcher.addResponseCallback(new PingResponse());
		
		dispatcher.addResponseCallback(new GuestGiveOrdersResponse());
		dispatcher.addResponseCallback(new ItemCreateBillResponse());
		dispatcher.addResponseCallback(new ItemCreateFoodResponse());
		dispatcher.addResponseCallback(new ItemSpoilFoodResponse());
		dispatcher.addResponseCallback(new ItemSetOwnerResponse());
		dispatcher.addResponseCallback(new ItemDestroyResponse());
		dispatcher.addResponseCallback(new ItemSetTypeResponse());
	}

}

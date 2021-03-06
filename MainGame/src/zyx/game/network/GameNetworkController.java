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
import zyx.game.network.callbacks.GuestNetworkCallbacks;
import zyx.game.ping.PingRequest;
import zyx.game.ping.PingResponse;
import zyx.game.position.CharacterMassPositionsResponse;
import zyx.game.position.CharacterPosRequest;
import zyx.game.world.entities.EntityInteractWithResponse;
import zyx.game.world.guests.*;
import zyx.game.world.items.*;
import zyx.game.world.player.*;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;

public class GameNetworkController extends BaseNetworkController
{

	public GameNetworkController()
	{
		addCallbackMap(new GameNetworkCallbacks());
		addCallbackMap(new ItemNetworkCallbacks());
		addCallbackMap(new GuestNetworkCallbacks());
		addCallbackMap(new PingPongNetworkCallbacks());
	}

	@Override
	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
		dispatcher.addRequestHandler(new CharacterLeaveGameRequest());
		dispatcher.addRequestHandler(new CharacterPosRequest());
		dispatcher.addRequestHandler(new LoginRequest());
		dispatcher.addRequestHandler(new PingRequest());
		dispatcher.addRequestHandler(new PlayerEnterOrderRequest());
		dispatcher.addRequestHandler(new PlayerGetOrderRequest());
		dispatcher.addRequestHandler(new PlayerGiveBillRequest());
		dispatcher.addRequestHandler(new PlayerGiveRequest());
		dispatcher.addRequestHandler(new PlayerPickupRequest());
		dispatcher.addRequestHandler(new PlayerPrintBillRequest());
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
		dispatcher.addResponseCallback(new GuestGrabFoodResponse());
		dispatcher.addResponseCallback(new GuestNoOrdersResponse());
		dispatcher.addResponseCallback(new GuestFullTableResponse());
		dispatcher.addResponseCallback(new GuestPayResponse());
		
		dispatcher.addResponseCallback(new ItemDropOnFloorResponse());
		dispatcher.addResponseCallback(new ItemCreateBillResponse());
		dispatcher.addResponseCallback(new ItemCreateFoodResponse());
		dispatcher.addResponseCallback(new ItemSpoilFoodResponse());
		dispatcher.addResponseCallback(new ItemSetOwnerResponse());
		dispatcher.addResponseCallback(new ItemDestroyResponse());
		dispatcher.addResponseCallback(new ItemSetFoodStateResponse());
		dispatcher.addResponseCallback(new ItemSetInUseResponse());
		
		dispatcher.addResponseCallback(new EntityInteractWithResponse());
	}

}

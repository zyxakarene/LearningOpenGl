package zyx.server.controller;

import zyx.game.world.guests.GuestGiveOrdersRequest;
import zyx.server.requests.CharacterJoinGameRequest;
import zyx.game.joining.CharacterLeaveGameRequest;
import zyx.game.joining.CharacterLeaveGameResponse;
import zyx.game.login.AuthenticateRequest;
import zyx.game.login.AuthenticateResponse;
import zyx.game.login.LoginRequest;
import zyx.game.login.LoginResponse;
import zyx.game.ping.PingRequest;
import zyx.game.ping.PingResponse;
import zyx.game.position.CharacterMassPositionsRequest;
import zyx.game.position.CharacterPosResponse;
import zyx.game.world.entities.EntityInteractWithRequest;
import zyx.game.world.guests.GuestGrabFoodRequest;
import zyx.game.world.guests.GuestNoOrdersRequest;
import zyx.game.world.guests.GuestPayRequest;
import zyx.game.world.items.*;
import zyx.game.world.player.*;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;
import zyx.server.requests.SetupGameRequest;

public class ServerNetworkController extends BaseNetworkController
{

	public ServerNetworkController()
	{
		addCallbackMap(new ServerNetworkCallbacks());
		addCallbackMap(new PlayerNetworkCallbacks());
	}

	@Override
	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
		dispatcher.addRequestHandler(new CharacterJoinGameRequest());
		dispatcher.addRequestHandler(new CharacterMassPositionsRequest());
		dispatcher.addRequestHandler(new CharacterLeaveGameRequest());
		dispatcher.addRequestHandler(new LoginRequest());
		dispatcher.addRequestHandler(new SetupGameRequest());
		dispatcher.addRequestHandler(new AuthenticateRequest());
		dispatcher.addRequestHandler(new PingRequest());
		
		dispatcher.addRequestHandler(new ItemCreateFoodRequest());
		dispatcher.addRequestHandler(new ItemCreateBillRequest());
		dispatcher.addRequestHandler(new GuestGiveOrdersRequest());
		dispatcher.addRequestHandler(new GuestPayRequest());
		dispatcher.addRequestHandler(new GuestGrabFoodRequest());
		dispatcher.addRequestHandler(new GuestNoOrdersRequest());
		dispatcher.addRequestHandler(new ItemDestroyRequest());
		dispatcher.addRequestHandler(new ItemSetOwnerRequest());
		dispatcher.addRequestHandler(new ItemSetTypeRequest());
		dispatcher.addRequestHandler(new ItemSetInUseRequest());
		dispatcher.addRequestHandler(new ItemSpoilFoodRequest());
		dispatcher.addRequestHandler(new ItemDropOnFloorRequest());
		
		dispatcher.addRequestHandler(new EntityInteractWithRequest());
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new LoginResponse());
		dispatcher.addResponseCallback(new CharacterLeaveGameResponse());
		dispatcher.addResponseCallback(new CharacterPosResponse());
		dispatcher.addResponseCallback(new AuthenticateResponse());
		dispatcher.addResponseCallback(new PingResponse());
		
		dispatcher.addResponseCallback(new PlayerEnterOrderResponse());
		dispatcher.addResponseCallback(new PlayerGetOrderResponse());
		dispatcher.addResponseCallback(new PlayerGiveBillResponse());
		dispatcher.addResponseCallback(new PlayerGiveResponse());
		dispatcher.addResponseCallback(new PlayerPickupResponse());
		dispatcher.addResponseCallback(new PlayerPrintBillResponse());
	}
}

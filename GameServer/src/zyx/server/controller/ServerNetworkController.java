package zyx.server.controller;

import zyx.game.joining.PlayerJoinGameRequest;
import zyx.game.joining.PlayerJoinGameResponse;
import zyx.game.joining.PlayerLeaveGameRequest;
import zyx.game.joining.PlayerLeaveGameResponse;
import zyx.game.login.AuthenticateRequest;
import zyx.game.login.AuthenticateResponse;
import zyx.game.login.LoginRequest;
import zyx.game.login.LoginResponse;
import zyx.game.ping.PingRequest;
import zyx.game.ping.PingResponse;
import zyx.game.position.PlayerMassPositionsRequest;
import zyx.game.position.PlayerPosResponse;
import zyx.game.world.entities.EntityInteractWithRequest;
import zyx.game.world.items.*;
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
		dispatcher.addRequestHandler(new PlayerJoinGameRequest());
		dispatcher.addRequestHandler(new PlayerMassPositionsRequest());
		dispatcher.addRequestHandler(new PlayerLeaveGameRequest());
		dispatcher.addRequestHandler(new LoginRequest());
		dispatcher.addRequestHandler(new SetupGameRequest());
		dispatcher.addRequestHandler(new AuthenticateRequest());
		dispatcher.addRequestHandler(new PingRequest());
		
		dispatcher.addRequestHandler(new ItemCreateFoodRequest());
		dispatcher.addRequestHandler(new ItemCreateBillRequest());
		dispatcher.addRequestHandler(new ItemCreateOrdersRequest());
		dispatcher.addRequestHandler(new ItemAddOrderRequest());
		dispatcher.addRequestHandler(new ItemDestroyRequest());
		dispatcher.addRequestHandler(new ItemSetOwnerRequest());
		dispatcher.addRequestHandler(new ItemSetTypeRequest());
		
		dispatcher.addRequestHandler(new EntityInteractWithRequest());
	}

	@Override
	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
		dispatcher.addResponseCallback(new LoginResponse());
		dispatcher.addResponseCallback(new PlayerJoinGameResponse());
		dispatcher.addResponseCallback(new PlayerLeaveGameResponse());
		dispatcher.addResponseCallback(new PlayerPosResponse());
		dispatcher.addResponseCallback(new AuthenticateResponse());
		dispatcher.addResponseCallback(new PingResponse());
		
		dispatcher.addResponseCallback(new ItemCreateFoodResponse());
		dispatcher.addResponseCallback(new ItemSetOwnerResponse());
	}

}

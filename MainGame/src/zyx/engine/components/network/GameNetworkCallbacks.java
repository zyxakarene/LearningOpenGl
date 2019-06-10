package zyx.engine.components.network;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.components.GameObject;
import zyx.game.network.movement.UpdatePlayerPositionResponse;
import zyx.game.scene.dragon.DragonScene;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.utils.cheats.Print;

public class GameNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onLogin;
	private INetworkCallback onPlayerJoined;
	private INetworkCallback onPlayerPos;

	public GameNetworkCallbacks()
	{
		createCallbacks();
		
		registerCallback(NetworkCommands.LOGIN, onLogin);
		registerCallback(NetworkCommands.PLAYER_JOINED_GAME, onPlayerJoined);
		registerCallback(NetworkCommands.PLAYER_UPDATE_POSITION, onPlayerPos);
	}

	private void onLogin(int id)
	{
		DragonScene.current.onAuthed(id);
		
		Print.out("User authenticated as ID:", id);
		NetworkChannel.sendRequest(NetworkCommands.PLAYER_JOINED_GAME, id);
	}

	private void onJoin(int id)
	{
		Print.out("User", id, "joined my game!");
		
		DragonScene.current.addPlayer(id);
	}
	
	private void onPosition(UpdatePlayerPositionResponse.PlayerPosData posData)
	{
		GameObject player = DragonScene.current.players.get(posData.playerId);
		if (player != null)
		{
			Vector3f pos = posData.vec;
			player.setPosition(false, pos);
		}
	}
	
	private void createCallbacks()
	{
		onLogin = (INetworkCallback<Integer>) (Integer playerId) ->
		{
			onLogin(playerId);
		};
		
		onPlayerJoined = (INetworkCallback<Integer>) (Integer playerId) ->
		{
			onJoin(playerId);
		};
		
		onPlayerPos = (INetworkCallback<UpdatePlayerPositionResponse.PlayerPosData>) (UpdatePlayerPositionResponse.PlayerPosData position) ->
		{
			onPosition(position);
		};
	}
}

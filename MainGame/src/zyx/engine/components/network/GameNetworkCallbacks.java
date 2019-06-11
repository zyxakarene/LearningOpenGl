package zyx.engine.components.network;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.freefly.OnlinePositionInterpolator;
import zyx.game.components.GameObject;
import zyx.game.scene.PlayerHandler;
import zyx.game.scene.dragon.DragonScene;
import zyx.game.vo.PlayerPositionData;
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

	private PlayerHandler playerHandler;
	
	public GameNetworkCallbacks(PlayerHandler playerHandler)
	{
		this.playerHandler = playerHandler;
		
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
		
		playerHandler.addPlayer(id, new Vector3f(0, 0, 10));
	}
	
	private void onPosition(PlayerPositionData data)
	{
		GameObject player = playerHandler.getPlayerById(data.id);
		if (player != null)
		{
			OnlinePositionInterpolator moveBehavior = (OnlinePositionInterpolator) player.getBehaviorById(BehaviorType.ONLINE_POSITION);
			if (moveBehavior != null)
			{
				moveBehavior.setPosition(data.position, data.rotation);
			}
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
		
		onPlayerPos = (INetworkCallback<PlayerPositionData>) (PlayerPositionData position) ->
		{
			onPosition(position);
		};
	}
}

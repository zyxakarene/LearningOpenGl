package zyx.engine.components.network;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.freefly.OnlinePositionInterpolator;
import zyx.game.components.GameObject;
import zyx.game.models.GameModels;
import zyx.game.network.joining.GameSetupPlayerInfo;
import zyx.game.network.joining.GameSetupVo;
import zyx.game.scene.PlayerHandler;
import zyx.game.scene.dragon.DragonScene;
import zyx.game.vo.AuthenticationData;
import zyx.game.vo.PlayerPositionData;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.utils.cheats.Print;

public class GameNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onAuthenticate;
	private INetworkCallback onPlayerJoined;
	private INetworkCallback onPlayerPos;
	private INetworkCallback onGameSetup;

	private PlayerHandler playerHandler;
	
	public GameNetworkCallbacks(PlayerHandler playerHandler)
	{
		this.playerHandler = playerHandler;
		
		createCallbacks();
		
		registerCallback(NetworkCommands.AUTHENTICATE, onAuthenticate);
		registerCallback(NetworkCommands.SETUP_GAME, onGameSetup);
		registerCallback(NetworkCommands.PLAYER_JOINED_GAME, onPlayerJoined);
		registerCallback(NetworkCommands.PLAYER_UPDATE_POSITION, onPlayerPos);
	}

	private void onAuthenticate(AuthenticationData data)
	{
		GameModels.player.playerId = data.id;
		GameModels.player.playerName = data.name;
		DragonScene.current.onAuthed();
		
		Print.out("User authenticated as ID:", data.id);
	}

	private void onJoin(int id)
	{
		Print.out("User", id, "joined my game!");
		
		playerHandler.addPlayer(id, new Vector3f(0, 0, 10), new Vector3f(0, 0, 1));
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
		onAuthenticate = (INetworkCallback<AuthenticationData>) (AuthenticationData data) ->
		{
			onAuthenticate(data);
		};
		
		onPlayerJoined = (INetworkCallback<Integer>) (Integer playerId) ->
		{
			onJoin(playerId);
		};
		
		onPlayerPos = (INetworkCallback<PlayerPositionData>) (PlayerPositionData position) ->
		{
			onPosition(position);
		};
		
		onGameSetup = (INetworkCallback<GameSetupVo>) (GameSetupVo data) ->
		{
			onGameSetup(data);
		};
	}

	private void onGameSetup(GameSetupVo setup)
	{
		Print.out("There's already", setup.players.length, "other players in this game");
		
		for (GameSetupPlayerInfo player : setup.players)
		{
			playerHandler.addPlayer(player.id, player.pos, player.rot);
		}
	}
}

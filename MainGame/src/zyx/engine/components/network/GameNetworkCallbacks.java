package zyx.engine.components.network;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.freefly.OnlinePositionInterpolator;
import zyx.game.components.GameObject;
import zyx.game.joining.data.CharacterJoinedData;
import zyx.game.models.GameModels;
import zyx.game.network.PingManager;
import zyx.game.joining.data.GameSetupPlayerInfo;
import zyx.game.joining.data.GameSetupVo;
import zyx.game.scene.PlayerHandler;
import zyx.game.scene.game.DragonScene;
import zyx.game.login.data.AuthenticationData;
import zyx.game.position.data.PlayerMassPositionData;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.utils.cheats.Print;

public class GameNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onAuthenticate;
	private INetworkCallback onCharacterJoined;
	private INetworkCallback onCharacterLeft;
	private INetworkCallback onCharacterMassPos;
	private INetworkCallback onGameSetup;

	private PlayerHandler playerHandler;

	public GameNetworkCallbacks(PlayerHandler playerHandler)
	{
		this.playerHandler = playerHandler;

		createCallbacks();

		registerCallback(NetworkCommands.AUTHENTICATE, onAuthenticate);
		registerCallback(NetworkCommands.SETUP_GAME, onGameSetup);
		registerCallback(NetworkCommands.CHARACTER_JOINED_GAME, onCharacterJoined);
		registerCallback(NetworkCommands.CHARACTER_LEFT_GAME, onCharacterLeft);
		registerCallback(NetworkCommands.PLAYER_MASS_POSITION, onCharacterMassPos);
	}

	private void onAuthenticate(AuthenticationData data)
	{
		PingManager.getInstance().addEntity(data.id);

		GameModels.player.playerId = data.id;
		GameModels.player.playerName = data.name;
		DragonScene.current.onAuthed();

		Print.out("User authenticated as ID:", data.id);
	}

	private void onJoin(CharacterJoinedData data)
	{
		Print.out(data.joinCount, "new characters joined my game!");
		for (int i = 0; i < data.joinCount; i++)
		{
			int id = data.ids[i];
			Print.out("Character", id, "joined my game!");
			playerHandler.addPlayer(id, data.positions[i], data.lookAts[i]);
		}

	}

	private void onLeave(int id)
	{
		Print.out("User", id, "left my game!");

		playerHandler.removePlayer(id);
	}

	private void onPosition(PlayerMassPositionData data)
	{
		int[] ids = data.ids;
		Vector3f[] positions = data.positions;
		Vector3f[] rotations = data.lookAts;

		int count = data.count;
		for (int i = 0; i < count; i++)
		{
			GameObject player = playerHandler.getPlayerById(ids[i]);

			if (player != null)
			{
				OnlinePositionInterpolator moveBehavior = (OnlinePositionInterpolator) player.getBehaviorById(BehaviorType.ONLINE_POSITION);
				if (moveBehavior != null)
				{
					moveBehavior.setPosition(positions[i], rotations[i]);
				}
			}
		}
	}

	private void createCallbacks()
	{
		onAuthenticate = (INetworkCallback<AuthenticationData>) (AuthenticationData data)
				-> 
				{
					onAuthenticate(data);
		};

		onCharacterJoined = (INetworkCallback<CharacterJoinedData>) (CharacterJoinedData data)
				-> 
				{
					onJoin(data);
		};

		onCharacterLeft = (INetworkCallback<Integer>) (Integer playerId)
				-> 
				{
					onLeave(playerId);
		};

		onCharacterMassPos = (INetworkCallback<PlayerMassPositionData>) (PlayerMassPositionData position)
				-> 
				{
					onPosition(position);
		};

		onGameSetup = (INetworkCallback<GameSetupVo>) (GameSetupVo data)
				-> 
				{
					onGameSetup(data);
		};
	}

	private void onGameSetup(GameSetupVo setup)
	{
		Print.out("There's already", setup.players.length, "other players in this game");

		for (GameSetupPlayerInfo player : setup.players)
		{
			playerHandler.addPlayer(player.id, player.pos, player.lookAt);
		}
	}
}

package zyx.game.network.callbacks;

import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.World3D;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.player.OnlinePositionInterpolator;
import zyx.game.components.GameObject;
import zyx.game.components.world.characters.CharacterSetupVo;
import zyx.game.components.world.characters.GameCharacter;
import zyx.game.components.world.furniture.BaseFurnitureItem;
import zyx.game.components.world.furniture.FurnitureSetupVo;
import zyx.game.joining.data.CharacterJoinedData;
import zyx.game.joining.data.FurnitureSetupData;
import zyx.game.models.GameModels;
import zyx.game.network.PingManager;
import zyx.game.joining.data.GameSetupVo;
import zyx.game.scene.ItemHolderHandler;
import zyx.game.scene.game.DragonScene;
import zyx.game.login.data.AuthenticationData;
import zyx.game.position.data.CharacterMassPositionData;
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

	private ItemHolderHandler itemHolderHandler;

	private HashMap<Integer, GameCharacter> characterMap;

	public GameNetworkCallbacks(ItemHolderHandler playerHandler)
	{
		this.itemHolderHandler = playerHandler;
		this.characterMap = new HashMap<>();

		createCallbacks();

		registerCallback(NetworkCommands.AUTHENTICATE, onAuthenticate);
		registerCallback(NetworkCommands.SETUP_GAME, onGameSetup);
		registerCallback(NetworkCommands.CHARACTER_JOINED_GAME, onCharacterJoined);
		registerCallback(NetworkCommands.CHARACTER_LEFT_GAME, onCharacterLeft);
		registerCallback(NetworkCommands.CHARACTER_MASS_POSITION, onCharacterMassPos);
	}

	private void onAuthenticate(AuthenticationData data)
	{
		PingManager.getInstance().addEntity(data.id);

		GameModels.player.playerId = data.id;
		GameModels.player.playerName = data.name;
		DragonScene.current.onAuthed();

		Print.out("User authenticated as ID:", data.id);
	}

	private void onCharacterJoined(CharacterJoinedData data)
	{
		CharacterSetupVo vo = new CharacterSetupVo();

		Print.out(data.joinCount, "new characters joined my game!");
		for (int i = 0; i < data.joinCount; i++)
		{
			vo.fromData(data, i);

			GameCharacter character = new GameCharacter();
			character.load(vo);

			int id = data.ids[i];
			Print.out("Character", id, "joined my game!");
			itemHolderHandler.addItemHolder(id, character);
			characterMap.put(id, character);

			World3D.instance.addChild(character);
		}
	}
	
	private void onFurnitureAdded(FurnitureSetupData data)
	{
		FurnitureSetupVo vo = new FurnitureSetupVo();

		for (int i = 0; i < data.furnitureCount; i++)
		{
			vo.fromData(data, i);

			BaseFurnitureItem furniture = vo.createFurniture();
			furniture.load(vo);

			int id = data.ids[i];
			Print.out("Furniture", id, "was added to the world");
			itemHolderHandler.addItemHolder(id, furniture);

			World3D.instance.addChild(furniture);
		}
	}

	private void onCharacterLeft(int id)
	{
		Print.out("User", id, "left my game!");

		itemHolderHandler.remove(id);
	}

	private void onCharacterMassPosition(CharacterMassPositionData data)
	{
		int[] ids = data.ids;
		Vector3f[] positions = data.positions;
		Vector3f[] lookAts = data.lookAts;

		int count = data.count;
		for (int i = 0; i < count; i++)
		{
			GameObject character = characterMap.get(ids[i]);

			if (character != null)
			{
				OnlinePositionInterpolator moveBehavior = (OnlinePositionInterpolator) character.getBehaviorById(BehaviorType.ONLINE_POSITION);
				if (moveBehavior != null)
				{
					moveBehavior.setPosition(positions[i], lookAts[i]);
				}
			}
		}
	}

	private void createCallbacks()
	{
		onAuthenticate = (INetworkCallback<AuthenticationData>) this::onAuthenticate;
		onCharacterJoined = (INetworkCallback<CharacterJoinedData>) this::onCharacterJoined;
		onCharacterLeft = (INetworkCallback<Integer>) this::onCharacterLeft;
		onCharacterMassPos = (INetworkCallback<CharacterMassPositionData>) this::onCharacterMassPosition;
		onGameSetup = (INetworkCallback<GameSetupVo>) this::onGameSetup;
	}

	private void onGameSetup(GameSetupVo setup)
	{
		Print.out("There's already", setup.players.joinCount, "other players in this game");
		Print.out("There's", setup.furniture.furnitureCount, "items in this game");
		
		onCharacterJoined(setup.players);
		onFurnitureAdded(setup.furniture);
	}
}
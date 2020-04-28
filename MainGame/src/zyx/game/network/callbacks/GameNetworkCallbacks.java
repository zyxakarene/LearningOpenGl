package zyx.game.network.callbacks;

import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.World3D;
import zyx.engine.scene.SceneManager;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.player.OnlinePositionInterpolator;
import zyx.game.components.GameObject;
import zyx.game.components.world.characters.CharacterSetupVo;
import zyx.game.components.world.characters.GameCharacter;
import zyx.game.components.world.furniture.BaseFurnitureItem;
import zyx.game.components.world.furniture.FurnitureSetupVo;
import zyx.game.components.world.items.BillItem;
import zyx.game.components.world.items.FoodItem;
import zyx.game.components.world.items.GameItem;
import zyx.game.joining.data.CharacterJoinedData;
import zyx.game.joining.data.FurnitureSetupData;
import zyx.game.models.GameModels;
import zyx.game.network.PingManager;
import zyx.game.joining.data.GameSetupVo;
import zyx.game.joining.data.ItemSetupData;
import zyx.game.scene.ItemHolderHandler;
import zyx.game.login.data.AuthenticationData;
import zyx.game.position.data.CharacterMassPositionData;
import zyx.game.scene.ItemHandler;
import zyx.game.scene.game.GameScene;
import zyx.game.vo.DishType;
import zyx.game.vo.FoodState;
import zyx.game.vo.HandheldItemType;
import zyx.game.world.guests.data.GuestOrderData;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.utils.cheats.Print;

public class GameNetworkCallbacks extends NetworkCallbacks
{

	private ItemHolderHandler itemHolderHandler;
	private ItemHandler itemHandler;

	private HashMap<Integer, GameCharacter> characterMap;

	public GameNetworkCallbacks(ItemHolderHandler playerHandler, ItemHandler itemHandler)
	{
		this.itemHolderHandler = playerHandler;
		this.itemHandler = itemHandler;
		this.characterMap = new HashMap<>();

		registerCallback(NetworkCommands.AUTHENTICATE, (INetworkCallback<AuthenticationData>) this::onAuthenticate);
		registerCallback(NetworkCommands.SETUP_GAME, (INetworkCallback<GameSetupVo>) this::onGameSetup);
		registerCallback(NetworkCommands.CHARACTER_JOINED_GAME, (INetworkCallback<CharacterJoinedData>) this::onCharacterJoined);
		registerCallback(NetworkCommands.CHARACTER_LEFT_GAME, (INetworkCallback<Integer>) this::onCharacterLeft);
		registerCallback(NetworkCommands.CHARACTER_MASS_POSITION, (INetworkCallback<CharacterMassPositionData>) this::onCharacterMassPosition);
		registerCallback(NetworkCommands.GUEST_GIVE_ORDER, (INetworkCallback<GuestOrderData>) this::onGiveOrder);
	}

	private void onAuthenticate(AuthenticationData data)
	{
		PingManager.getInstance().addEntity(data.id);

		GameModels.player.authenticated = true;
		GameModels.player.playerId = data.id;
		GameModels.player.playerName = data.name;

		GameScene scene = SceneManager.getInstance().<GameScene>getSceneAs();
		scene.createPlayerObject();
		
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

	private void onItemAdded(ItemSetupData items)
	{
		GameItem item;

		for (int i = 0; i < items.itemCount; i++)
		{
			int itemId = items.ids[i];
			int ownerId = items.ownerIds[i];
			HandheldItemType type = items.types[i];

			if (type == HandheldItemType.BILL)
			{
				item = new BillItem();
			}
			else
			{
				DishType dish = items.dishTypes[i];
				FoodState foodState = items.foodStates[i];
				FoodItem food = new FoodItem(dish);
				food.setSubType(foodState);
				if (items.dishSpoiled[i])
				{
					food.spoil();
				}

				item = food;
			}

			item.load();
			itemHandler.addItem(itemId, item, ownerId);
		}
	}

	private void onGameSetup(GameSetupVo setup)
	{
		Print.out("There's already", setup.characters.joinCount, "other players in this game");
		Print.out("There's", setup.furniture.furnitureCount, "furniture items in this game");
		Print.out("There's", setup.items.itemCount, "held items in this game");

		onCharacterJoined(setup.characters);
		onFurnitureAdded(setup.furniture);
		onItemAdded(setup.items);
	}

	private void onGiveOrder(GuestOrderData data)
	{
		GameCharacter guest = characterMap.get(data.characterId);

		if (guest != null)
		{
//			guest.TellDishAnimation();
			Print.out("Guest", guest, "wanted the dish", DishType.getFromId(data.dishTypeId));
		}
	}
}

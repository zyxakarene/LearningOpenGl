package zyx.game.network.callbacks;

import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.OnTeaPotClicked;
import zyx.engine.components.tooltips.TestTooltip;
import zyx.engine.components.tooltips.TooltipManager;
import zyx.engine.components.world.World3D;
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
import zyx.game.scene.game.DragonScene;
import zyx.game.login.data.AuthenticationData;
import zyx.game.position.data.CharacterMassPositionData;
import zyx.game.scene.ItemHandler;
import zyx.game.scene.game.GameScene;
import zyx.game.vo.DishType;
import zyx.game.vo.HandheldItemType;
import zyx.game.world.guests.data.GuestOrderData;
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
	private INetworkCallback onGiveOrders;

	private ItemHolderHandler itemHolderHandler;
	private ItemHandler itemHandler;

	private HashMap<Integer, GameCharacter> characterMap;

	public GameNetworkCallbacks(ItemHolderHandler playerHandler, ItemHandler itemHandler)
	{
		this.itemHolderHandler = playerHandler;
		this.itemHandler = itemHandler;
		this.characterMap = new HashMap<>();

		createCallbacks();

		registerCallback(NetworkCommands.AUTHENTICATE, onAuthenticate);
		registerCallback(NetworkCommands.SETUP_GAME, onGameSetup);
		registerCallback(NetworkCommands.CHARACTER_JOINED_GAME, onCharacterJoined);
		registerCallback(NetworkCommands.CHARACTER_LEFT_GAME, onCharacterLeft);
		registerCallback(NetworkCommands.CHARACTER_MASS_POSITION, onCharacterMassPos);
		registerCallback(NetworkCommands.GUEST_GIVE_ORDER, onGiveOrders);
	}

	private void onAuthenticate(AuthenticationData data)
	{
		PingManager.getInstance().addEntity(data.id);

		GameModels.player.authenticated = true;
		GameModels.player.playerId = data.id;
		GameModels.player.playerName = data.name;

		GameScene scene = GameScene.getCurrent();
		if (scene != null)
		{
			scene.createPlayerObject();
		}
		
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

			GameScene.getCurrent().addPickedObject(character.getPhysbox(), new OnTeaPotClicked());
			
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
				FoodItem food = new FoodItem(dish);
				if (items.dishSpoiled[i])
				{
					food.spoil();
				}

				item = food;
			}

			item.setType(type);
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
			Print.out("Guest", guest, "wanted the dish", data.dishType);
		}
	}

	private void createCallbacks()
	{
		onAuthenticate = (INetworkCallback<AuthenticationData>) this::onAuthenticate;
		onCharacterJoined = (INetworkCallback<CharacterJoinedData>) this::onCharacterJoined;
		onCharacterLeft = (INetworkCallback<Integer>) this::onCharacterLeft;
		onCharacterMassPos = (INetworkCallback<CharacterMassPositionData>) this::onCharacterMassPosition;
		onGameSetup = (INetworkCallback<GameSetupVo>) this::onGameSetup;
		onGiveOrders = (INetworkCallback<GuestOrderData>) this::onGiveOrder;
	}
}

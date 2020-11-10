package zyx.game.network.callbacks;

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
import zyx.game.login.data.AuthenticationData;
import zyx.game.position.data.CharacterMassPositionData;
import zyx.game.scene.game.GameScene;
import zyx.game.vo.DishType;
import zyx.game.vo.FoodState;
import zyx.game.vo.HandheldItemType;
import zyx.game.world.entities.data.EntityInteractData;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.utils.cheats.Print;

public class GameNetworkCallbacks extends AbstractDinerNetworkCallbacks
{

	public GameNetworkCallbacks()
	{
		registerCallback(NetworkCommands.AUTHENTICATE, (INetworkCallback<AuthenticationData>) this::onAuthenticate);
		registerCallback(NetworkCommands.SETUP_GAME, (INetworkCallback<GameSetupVo>) this::onGameSetup);
		registerCallback(NetworkCommands.CHARACTER_JOINED_GAME, (INetworkCallback<CharacterJoinedData>) this::onCharacterJoined);
		registerCallback(NetworkCommands.CHARACTER_LEFT_GAME, (INetworkCallback<Integer>) this::onCharacterLeft);
		registerCallback(NetworkCommands.CHARACTER_MASS_POSITION, (INetworkCallback<CharacterMassPositionData>) this::onCharacterMassPosition);
		registerCallback(NetworkCommands.ENTITY_INTERACT, (INetworkCallback<EntityInteractData>) this::onInteractWith);
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
		World3D world = World3D.getInstance();
		
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

			world.addChild(character);
		}
	}

	private void onFurnitureAdded(FurnitureSetupData data)
	{
		FurnitureSetupVo vo = new FurnitureSetupVo();
		World3D world = World3D.getInstance();
		
		for (int i = 0; i < data.furnitureCount; i++)
		{
			vo.fromData(data, i);

			BaseFurnitureItem furniture = vo.createFurniture();
			furniture.load(vo);
			
			int id = data.ids[i];
			Print.out("Furniture", id, "was added to the world");
			itemHolderHandler.addItemHolder(id, furniture);
			furnitureMap.put(id, furniture);

			int userId = data.usingIds[i];
			if (userId > 0)
			{
				GameCharacter user = characterMap.get(userId);
				if (user != null)
				{
					user.info.interacting = furniture;
				}
			}
			
			world.addChild(furniture);
		}
	}

	private void onCharacterLeft(int id)
	{
		Print.out("User", id, "left my game!");

		itemHolderHandler.remove(id);
	}
	
	private void onInteractWith(EntityInteractData data)
	{
		Print.out(data.userId, "interacted with", data.entityId, ":", data.started);
		
		BaseFurnitureItem furniture = furnitureMap.get(data.entityId);
		if (furniture != null)
		{
			furniture.interactWith(data.started);
		}
		
		GameCharacter character = characterMap.get(data.userId);
		if (character != null)
		{
			if (data.started)
			{
				character.info.interacting = furniture;
			}
			else
			{
				character.info.interacting = null;
			}
		}
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
}

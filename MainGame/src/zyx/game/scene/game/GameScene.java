package zyx.game.scene.game;

import java.util.ArrayList;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.meshbatch.MeshBatchManager;
import zyx.engine.components.tooltips.TooltipManager;
import zyx.engine.scene.Scene;
import zyx.engine.utils.worldpicker.WorldPicker;
import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.FreeFlyBehavior;
import zyx.game.behavior.player.OnlinePositionSender;
import zyx.game.components.GameObject;
import zyx.game.components.world.player.PlayerObject;
import zyx.game.models.GameModels;
import zyx.game.network.GameNetworkController;
import zyx.game.network.PingManager;
import zyx.game.scene.ItemHandler;
import zyx.game.scene.ItemHolderHandler;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.opengl.camera.Camera;
import zyx.utils.interfaces.IPhysbox;
import zyx.engine.utils.worldpicker.IWorldPickedItem;

public class GameScene extends Scene
{

	private WorldPicker picker;

	protected ItemHandler itemHandler;
	protected ItemHolderHandler itemHolderHandler;
	protected BaseNetworkController networkController;

	protected PlayerObject player;

	private ArrayList<GameObject> gameObjects;

	public GameScene()
	{
		gameObjects = new ArrayList<>();

		picker = new WorldPicker();
	}

	public void addPickedObject(IPhysbox object, IWorldPickedItem clickCallback)
	{
		picker.addObject(object, clickCallback);
	}

	public void removePickedObject(IPhysbox object, IWorldPickedItem clickCallback)
	{
		picker.removeObject(object, clickCallback);
	}

	public void addGameObject(GameObject object)
	{
		gameObjects.add(object);
	}

	public void removeGameObject(GameObject object)
	{
		gameObjects.remove(object);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);

		for (GameObject gameObject : gameObjects)
		{
			gameObject.update(timestamp, elapsedTime);
		}

		itemHolderHandler.update(timestamp, elapsedTime);
		picker.update();
	}

	@Override
	protected final void onInitialize()
	{
		itemHolderHandler = new ItemHolderHandler();
		itemHandler = new ItemHandler(itemHolderHandler);
		
		networkController = createNetworkDispatcher();
		networkController.addListeners();
		
		onInitializeGameScene();
	}

	protected void onInitializeGameScene()
	{
	}
	
	public void createPlayerObject()
	{
		player = new PlayerObject();

		player.addBehavior(new FreeFlyBehavior());
		player.addBehavior(new CameraUpdateViewBehavior());
		player.addBehavior(new OnlinePositionSender());
		Camera.getInstance().setViewObject(player);

		gameObjects.add(player);

		world.addChild(player);
	}

	@Override
	protected BaseNetworkController createNetworkDispatcher()
	{
		return new GameNetworkController(itemHolderHandler, itemHandler);
	}
	
	@Override
	protected void onDispose()
	{
		NetworkChannel.sendRequest(NetworkCommands.CHARACTER_LEFT_GAME, GameModels.player.playerId);
		PingManager.getInstance().removeEntity(GameModels.player.playerId);

		if (picker != null)
		{
			picker.dispose();
			picker = null;
		}

		if (networkController != null)
		{
			networkController.dispose();
			networkController = null;
		}

		if (gameObjects != null)
		{
			for (GameObject gameObject : gameObjects)
			{
				gameObject.dispose();
			}

			gameObjects.clear();
			gameObjects = null;
		}

		CubemapManager.getInstance().clean();
		TooltipManager.getInstance().clean();
		MeshBatchManager.getInstance().clean();

		if (itemHandler != null)
		{
			itemHandler.dispose();
			itemHandler = null;
		}

		if (itemHolderHandler != null)
		{
			itemHolderHandler.dispose();
			itemHolderHandler = null;
		}

		super.onDispose();
	}

}

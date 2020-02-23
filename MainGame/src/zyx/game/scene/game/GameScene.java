package zyx.game.scene.game;

import zyx.engine.scene.Scene;
import zyx.engine.utils.worldpicker.IHoveredItem;
import zyx.engine.utils.worldpicker.WorldPicker;
import zyx.game.scene.ItemHandler;
import zyx.game.scene.ItemHolderHandler;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.utils.interfaces.IPhysbox;

public class GameScene extends Scene
{

	private WorldPicker picker;

	protected ItemHandler itemHandler;
	protected ItemHolderHandler itemHolderHandler;
	protected BaseNetworkController networkController;

	public GameScene()
	{
		//picker = new WorldPicker();

		itemHolderHandler = new ItemHolderHandler();
		itemHandler = new ItemHandler(itemHolderHandler);
	}

	public static GameScene getCurrent()
	{
		return (GameScene) current;
	}
	
	public void addPickedObject(IPhysbox object, IHoveredItem clickCallback)
	{
//		picker.addObject(object, clickCallback);
	}

	public void removePickedObject(IPhysbox object, IHoveredItem clickCallback)
	{
//		picker.removeObject(object, clickCallback);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);

		itemHolderHandler.update(timestamp, elapsedTime);
//		picker.update();
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		networkController = createNetworkDispatcher();
		networkController.addListeners();
	}

	@Override
	protected void onDispose()
	{
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

		super.onDispose();
	}

}

package zyx.game.network.callbacks;

import zyx.game.components.world.items.BillItem;
import zyx.game.components.world.items.FoodItem;
import zyx.game.components.world.items.GameItem;
import zyx.game.scene.ItemHandler;
import zyx.game.world.items.data.ItemChangedData;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;

public class ItemNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onCreateFood;
	private INetworkCallback onDestroy;
	private INetworkCallback onCreateBill;
	private INetworkCallback onSetOwner;
	private INetworkCallback onSetType;
	private INetworkCallback onFoodSpoiled;
	private INetworkCallback onItemDropped;

	private ItemHandler itemHandler;

	public ItemNetworkCallbacks(ItemHandler itemHandler)
	{
		this.itemHandler = itemHandler;

		createCallbacks();

		registerCallback(NetworkCommands.ITEM_CREATE_FOOD, onCreateFood);
		registerCallback(NetworkCommands.ITEM_CREATE_BILL, onCreateBill);
		registerCallback(NetworkCommands.ITEM_DESTROY, onDestroy);
		registerCallback(NetworkCommands.ITEM_SET_OWNER, onSetOwner);
		registerCallback(NetworkCommands.ITEM_SET_TYPE, onSetType);
		registerCallback(NetworkCommands.ITEM_SPOIL_FOOD, onFoodSpoiled);
		registerCallback(NetworkCommands.ITEM_PUT_ON_FOOR, onItemDropped);
	}

	private void onItemDropped(ItemChangedData data)
	{
		itemHandler.setOwner(data.itemId, data.ownerId, data.position);
	}
	
	private void onCreateFood(ItemChangedData data)
	{
		FoodItem item = new FoodItem(data.dishType);
		item.load();
		itemHandler.addItem(data.itemId, item, data.ownerId);
	}

	private void onCreateBill(ItemChangedData data)
	{
		BillItem item = new BillItem();
		item.load();
		itemHandler.addItem(data.itemId, item, data.ownerId);
	}

	private void onDestroyItem(Integer uniqueId)
	{
		itemHandler.removeItem(uniqueId);
	}

	private void onFoodSpoiled(Integer uniqueId)
	{
		GameItem item = itemHandler.getItemById(uniqueId);
		if (item instanceof FoodItem)
		{
			FoodItem food = (FoodItem) item;
			food.spoil();
		}
	}

	private void onSetItemOwner(ItemChangedData data)
	{
		itemHandler.setOwner(data.itemId, data.ownerId);
	}

	private void onSetItemType(ItemChangedData data)
	{
		itemHandler.setType(data.itemId, data.type);
	}

	private void createCallbacks()
	{
		onItemDropped = (INetworkCallback<ItemChangedData>) this::onItemDropped;
		onCreateFood = (INetworkCallback<ItemChangedData>) this::onCreateFood;
		onDestroy = (INetworkCallback<Integer>) this::onDestroyItem;
		onFoodSpoiled = (INetworkCallback<Integer>) this::onFoodSpoiled;
		onCreateBill = (INetworkCallback<ItemChangedData>) this::onCreateBill;
		onSetOwner = (INetworkCallback<ItemChangedData>) this::onSetItemOwner;
		onSetType = (INetworkCallback<ItemChangedData>) this::onSetItemType;
	}
}

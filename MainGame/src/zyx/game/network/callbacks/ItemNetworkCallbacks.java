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

	private ItemHandler itemHandler;

	public ItemNetworkCallbacks(ItemHandler itemHandler)
	{
		this.itemHandler = itemHandler;

		registerCallback(NetworkCommands.ITEM_CREATE_FOOD, (INetworkCallback<ItemChangedData>) this::onCreateFood);
		registerCallback(NetworkCommands.ITEM_CREATE_BILL, (INetworkCallback<ItemChangedData>) this::onCreateBill);
		registerCallback(NetworkCommands.ITEM_DESTROY, (INetworkCallback<Integer>) this::onDestroyItem);
		registerCallback(NetworkCommands.ITEM_SET_OWNER, (INetworkCallback<ItemChangedData>) this::onSetItemOwner);
		registerCallback(NetworkCommands.ITEM_SET_FOOD_STATE, (INetworkCallback<ItemChangedData>) this::onSetFoodState);
		registerCallback(NetworkCommands.ITEM_SPOIL_FOOD, (INetworkCallback<Integer>) this::onFoodSpoiled);
		registerCallback(NetworkCommands.ITEM_PUT_ON_FOOR, (INetworkCallback<ItemChangedData>) this::onItemDropped);
		registerCallback(NetworkCommands.ITEM_SET_IN_USE, (INetworkCallback<ItemChangedData>) this::onItemSetInUse);
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

	private void onItemSetInUse(ItemChangedData data)
	{
		itemHandler.setItemInUse(data.itemId, data.inUse);
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

	private void onSetFoodState(ItemChangedData data)
	{
		itemHandler.setSubType(data.itemId, data.foodState);
	}
}

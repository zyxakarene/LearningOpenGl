package zyx.game.network.callbacks;

import zyx.game.components.world.items.BillItem;
import zyx.game.components.world.items.FoodItem;
import zyx.game.components.world.items.GameItem;
import zyx.game.vo.DishType;
import zyx.game.vo.FoodState;
import zyx.game.world.items.data.ItemChangedData;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;

public class ItemNetworkCallbacks extends AbstractDinerNetworkCallbacks
{
	public ItemNetworkCallbacks()
	{
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
		DishType type = DishType.getFromId(data.dishTypeId);
		FoodItem item = new FoodItem(type);
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
		FoodState state = FoodState.getFromId(data.foodStateId);
		itemHandler.setSubType(data.itemId, state);
	}
}

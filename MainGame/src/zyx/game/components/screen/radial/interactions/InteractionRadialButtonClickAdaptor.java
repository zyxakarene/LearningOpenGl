package zyx.game.components.screen.radial.interactions;

import zyx.game.components.screen.radial.*;
import java.util.HashMap;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.models.GameModels;
import zyx.game.network.services.PlayerService;
import zyx.game.vo.DishType;

public class InteractionRadialButtonClickAdaptor extends RadialButtonClickAdaptor
{
	@Override
	protected void addCallbacks(HashMap<IRadialMenuOption, ICallback<InteractableContainer>> callbackMap)
	{
		callbackMap.put(InteractionAction.ADD_ORDER, this::onAddOrderClicked);
		callbackMap.put(InteractionAction.CLEANUP, this::onCleanupClicked);
		callbackMap.put(InteractionAction.PLACE, this::onPlaceClicked);
		callbackMap.put(InteractionAction.SERVE, this::onServeClicked);
		callbackMap.put(InteractionAction.SERVE_BILL, this::onServeBillClicked);
		callbackMap.put(InteractionAction.PRINT_BILL, this::onPrintBillClicked);
		callbackMap.put(InteractionAction.TAKE, this::onTakeClicked);
		callbackMap.put(InteractionAction.TAKE_ORDER, this::onTakeOrderClicked);
	}
	
	private void onAddOrderClicked(InteractableContainer data)
	{
		closeRadial();
		PlayerService.enterOrder(DishType.STEAK);
	}

	private void onCleanupClicked(InteractableContainer data)
	{
		closeRadial();
		PlayerService.interactWith(GameModels.selection.lastInteractedFurniture);
	}

	private void onPlaceClicked(InteractableContainer data)
	{
		closeRadial();
		PlayerService.giveItem(GameModels.selection.lastInteractedFurniture);
	}

	private void onServeClicked(InteractableContainer data)
	{
		closeRadial();
		PlayerService.giveItem(GameModels.selection.lastInteractedFurniture);
	}

	private void onServeBillClicked(InteractableContainer data)
	{
		closeRadial();
		PlayerService.giveBill(GameModels.selection.lastInteractedFurniture);
	}

	private void onPrintBillClicked(InteractableContainer data)
	{
		closeRadial();
		PlayerService.printBill();
	}

	private void onTakeClicked(InteractableContainer data)
	{
		closeRadial();
		PlayerService.pickupItem(GameModels.selection.lastInteractedItem);
	}

	private void onTakeOrderClicked(InteractableContainer data)
	{
		closeRadial();
		PlayerService.getOrder(GameModels.selection.lastInteractedCharacter);
	}
}

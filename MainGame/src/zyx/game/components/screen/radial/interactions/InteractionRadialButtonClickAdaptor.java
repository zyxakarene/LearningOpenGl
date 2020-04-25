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

	private ICallback<InteractableContainer> addOrderClick;
	private ICallback<InteractableContainer> cleanupClick;
	private ICallback<InteractableContainer> closeClick;
	private ICallback<InteractableContainer> placeClick;
	private ICallback<InteractableContainer> serveClick;
	private ICallback<InteractableContainer> serveBillClick;
	private ICallback<InteractableContainer> printBillClick;
	private ICallback<InteractableContainer> takeClick;
	private ICallback<InteractableContainer> takeOrderClick;
	
	public InteractionRadialButtonClickAdaptor()
	{
		addOrderClick = this::onAddOrderClicked;
		cleanupClick = this::onCleanupClicked;
		closeClick = this::onCloseClicked;
		placeClick = this::onPlaceClicked;
		serveClick = this::onServeClicked;
		serveBillClick = this::onServeBillClicked;
		printBillClick = this::onPrintBillClicked;
		takeClick = this::onTakeClicked;
		takeOrderClick = this::onTakeOrderClicked;
	}

	@Override
	protected void addCallbacks(HashMap<IRadialMenuOption, ICallback<InteractableContainer>> callbackMap)
	{
		callbackMap.put(InteractionAction.ADD_ORDER, addOrderClick);
		callbackMap.put(InteractionAction.CLEANUP, cleanupClick);
		callbackMap.put(InteractionAction.CLOSE, closeClick);
		callbackMap.put(InteractionAction.PLACE, placeClick);
		callbackMap.put(InteractionAction.SERVE, serveClick);
		callbackMap.put(InteractionAction.SERVE_BILL, serveBillClick);
		callbackMap.put(InteractionAction.PRINT_BILL, printBillClick);
		callbackMap.put(InteractionAction.TAKE, takeClick);
		callbackMap.put(InteractionAction.TAKE_ORDER, takeOrderClick);
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
	
	@Override
	protected void onDispose()
	{
		addOrderClick = null;
		cleanupClick = null;
		closeClick = null;
		placeClick = null;
		serveClick = null;
		serveBillClick = null;
		takeClick = null;
		takeOrderClick = null;
	}
}

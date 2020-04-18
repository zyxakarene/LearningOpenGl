package zyx.game.components.screen.radial;

import java.util.HashMap;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.models.GameModels;
import zyx.game.network.services.PlayerService;
import zyx.game.vo.DishType;

class RadialButtonClickAdaptor
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

	private HashMap<InteractionAction, ICallback<InteractableContainer>> callbackMap;
	private RadialMenu radialMenu;
	
	RadialButtonClickAdaptor(RadialMenu radialMenu)
	{
		this.radialMenu = radialMenu;
		
		addOrderClick = this::onAddOrderClicked;
		cleanupClick = this::onCleanupClicked;
		closeClick = this::onCloseClicked;
		placeClick = this::onPlaceClicked;
		serveClick = this::onServeClicked;
		serveBillClick = this::onServeBillClicked;
		printBillClick = this::onPrintBillClicked;
		takeClick = this::onTakeClicked;
		takeOrderClick = this::onTakeOrderClicked;

		callbackMap = new HashMap<>();
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

	ICallback<InteractableContainer> getCallback(InteractionAction action)
	{
		return callbackMap.get(action);
	}
	
	private void onCloseClicked(InteractableContainer data)
	{
		closeRadial();
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

	private void closeRadial()
	{
		radialMenu.visible = false;
	}
	
	void dispose()
	{
		if (callbackMap != null)
		{
			callbackMap.clear();
			callbackMap = null;
		}

		radialMenu = null;
		
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

package zyx.game.components.screen.radial.interactions;

import zyx.game.components.screen.radial.*;
import java.util.HashMap;
import zyx.engine.scene.SceneManager;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.models.GameModels;
import zyx.game.network.services.PlayerService;
import zyx.game.scene.game.DinerScene;

public class InteractionRadialButtonClickAdaptor extends RadialButtonClickAdaptor
{

	private final DinerScene scene;

	public InteractionRadialButtonClickAdaptor()
	{
		scene = SceneManager.getInstance().getSceneAs();
	}

	@Override
	protected void addCallbacks(HashMap<Integer, ICallback<RadialMenuItemRenderer>> callbackMap)
	{
		callbackMap.put(InteractionAction.ADD_ORDER.id, this::onAddOrderClicked);
		callbackMap.put(InteractionAction.CLEANUP.id, this::onCleanupClicked);
		callbackMap.put(InteractionAction.PLACE.id, this::onPlaceClicked);
		callbackMap.put(InteractionAction.SERVE.id, this::onServeClicked);
		callbackMap.put(InteractionAction.SERVE_BILL.id, this::onServeBillClicked);
		callbackMap.put(InteractionAction.PRINT_BILL.id, this::onPrintBillClicked);
		callbackMap.put(InteractionAction.TAKE.id, this::onTakeClicked);
		callbackMap.put(InteractionAction.TAKE_ORDER.id, this::onTakeOrderClicked);
	}

	private void onAddOrderClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();

		if(scene != null)
		{
			scene.dinerHud.showFood();
		}
	}

	private void onCleanupClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();
		PlayerService.interactWith(GameModels.selection.lastInteractedFurniture);
	}

	private void onPlaceClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();
		PlayerService.giveItem(GameModels.selection.lastInteractedFurniture);
	}

	private void onServeClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();
		PlayerService.giveItem(GameModels.selection.lastInteractedFurniture);
	}

	private void onServeBillClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();
		PlayerService.giveBill(GameModels.selection.lastInteractedFurniture);
	}

	private void onPrintBillClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();
		PlayerService.printBill();
	}

	private void onTakeClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();
		PlayerService.pickupItem(GameModels.selection.lastInteractedItem);
	}

	private void onTakeOrderClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();
		PlayerService.getOrder(GameModels.selection.lastInteractedCharacter);
	}
}

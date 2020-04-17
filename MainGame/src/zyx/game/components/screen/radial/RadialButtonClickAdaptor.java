package zyx.game.components.screen.radial;

import java.util.HashMap;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.scene.Scene;
import zyx.engine.scene.SceneManager;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.scene.game.DinerScene;

class RadialButtonClickAdaptor
{

	private ICallback<InteractableContainer> addOrderClick;
	private ICallback<InteractableContainer> cleanupClick;
	private ICallback<InteractableContainer> closeClick;
	private ICallback<InteractableContainer> placeClick;
	private ICallback<InteractableContainer> serveClick;
	private ICallback<InteractableContainer> serveBillClick;
	private ICallback<InteractableContainer> takeClick;
	private ICallback<InteractableContainer> takeOrderClick;

	private HashMap<InteractionAction, ICallback<InteractableContainer>> callbackMap;

	RadialButtonClickAdaptor()
	{
		addOrderClick = this::onaddOrderClicked;
		cleanupClick = this::oncleanupClicked;
		closeClick = this::onCloseClicked;
		placeClick = this::onplaceClicked;
		serveClick = this::onserveClicked;
		serveBillClick = this::onserveBillClicked;
		takeClick = this::ontakeClicked;
		takeOrderClick = this::ontakeOrderClicked;

		callbackMap = new HashMap<>();
		callbackMap.put(InteractionAction.ADD_ORDER, addOrderClick);
		callbackMap.put(InteractionAction.CLEANUP, cleanupClick);
		callbackMap.put(InteractionAction.CLOSE, closeClick);
		callbackMap.put(InteractionAction.PLACE, placeClick);
		callbackMap.put(InteractionAction.SERVE, serveClick);
		callbackMap.put(InteractionAction.SERVE_BILL, serveBillClick);
		callbackMap.put(InteractionAction.TAKE, takeClick);
		callbackMap.put(InteractionAction.TAKE_ORDER, takeOrderClick);
	}

	ICallback<InteractableContainer> getCallback(InteractionAction action)
	{
		return callbackMap.get(action);
	}

	private void onaddOrderClicked(InteractableContainer data)
	{
		closeRadial();
	}

	private void oncleanupClicked(InteractableContainer data)
	{
		closeRadial();
	}

	private void onCloseClicked(InteractableContainer data)
	{
		closeRadial();
	}

	private void onplaceClicked(InteractableContainer data)
	{
		closeRadial();
	}

	private void onserveClicked(InteractableContainer data)
	{
		closeRadial();
	}

	private void onserveBillClicked(InteractableContainer data)
	{
		closeRadial();
	}

	private void ontakeClicked(InteractableContainer data)
	{
		closeRadial();
	}

	private void ontakeOrderClicked(InteractableContainer data)
	{
		closeRadial();
	}

	private void closeRadial()
	{
		DinerScene scene = SceneManager.getInstance().getSceneAs();
		if (scene != null && scene.dinerHud != null)
		{
			scene.dinerHud.hideRadialMenu();
		}
	}
	
	void dispose()
	{
		if (callbackMap != null)
		{
			callbackMap.clear();
			callbackMap = null;
		}

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

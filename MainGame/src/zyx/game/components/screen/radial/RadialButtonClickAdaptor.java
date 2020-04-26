package zyx.game.components.screen.radial;

import java.util.HashMap;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;

public abstract class RadialButtonClickAdaptor
{
	private HashMap<IRadialMenuOption, ICallback<InteractableContainer>> callbackMap;
	private RadialMenu radialMenu;
	
	public RadialButtonClickAdaptor()
	{
		callbackMap = new HashMap<>();
	}
	
	void createCallbacks()
	{
		addCallbacks(callbackMap);
	}

	protected abstract void addCallbacks(HashMap<IRadialMenuOption, ICallback<InteractableContainer>> callbackMap);
	
	void setRadialMenu(RadialMenu radialMenu)
	{
		this.radialMenu = radialMenu;
	}

	ICallback<InteractableContainer> getCallback(IRadialMenuOption action)
	{
		return callbackMap.get(action);
	}
	
	protected final void closeRadial()
	{
		if(radialMenu != null)
		{
			radialMenu.visible = false;
		}
	}
	
	void dispose()
	{
		if (callbackMap != null)
		{
			callbackMap.clear();
			callbackMap = null;
		}

		radialMenu = null;
		
		onDispose();
	}

	protected void onDispose()
	{
	}
}

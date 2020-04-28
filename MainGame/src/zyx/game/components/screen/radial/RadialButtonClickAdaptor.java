package zyx.game.components.screen.radial;

import java.util.HashMap;
import zyx.engine.utils.callbacks.ICallback;

public abstract class RadialButtonClickAdaptor
{
	private HashMap<Integer, ICallback<RadialMenuItemRenderer>> callbackMap;
	private RadialMenu radialMenu;
	
	public RadialButtonClickAdaptor()
	{
		callbackMap = new HashMap<>();
	}
	
	void createCallbacks()
	{
		addCallbacks(callbackMap);
	}

	protected abstract void addCallbacks(HashMap<Integer, ICallback<RadialMenuItemRenderer>> callbackMap);
	
	void setRadialMenu(RadialMenu radialMenu)
	{
		this.radialMenu = radialMenu;
	}

	ICallback<RadialMenuItemRenderer> getCallback(IRadialMenuOption action)
	{
		return callbackMap.get(action.getUniqueId());
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
	}
}

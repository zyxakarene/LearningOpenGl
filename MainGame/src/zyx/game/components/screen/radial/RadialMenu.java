package zyx.game.components.screen.radial;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.screen.json.JsonSprite;
import zyx.utils.FloatMath;


public abstract class RadialMenu extends JsonSprite
{

	private HashMap<IRadialMenuOption, RadialMenuItemRenderer> optionButtonMap;
	private RadialButtonClickAdaptor adaptor;
	private ArrayList<RadialMenuItemRenderer> renderers;
	
	private IRadialMenuOption[] allOptions;
	
	@Override
	protected void onPreInitialize()
	{
		optionButtonMap = new HashMap<>();
		renderers = new ArrayList<>();
		
		allOptions = getAllPossibilities();
		
		adaptor = getClickAdaptor();
		adaptor.createCallbacks();
		adaptor.setRadialMenu(this);
	}
	
	protected abstract RadialButtonClickAdaptor getClickAdaptor();
	protected abstract IRadialMenuOption[] getAllPossibilities();

	@Override
	protected String[] getDependencies()
	{
		return new String[]
		{
			"json.interaction.radial_menu_button_renderer"
		};
	}

	@Override
	public String getResource()
	{
		return "json.interaction.radial_menu";
	}

	@Override
	protected void onComponentsCreated()
	{
		RadialMenuItemRenderer closeRenderer = new RadialMenuItemRenderer("icon_close", "Close");
		closeRenderer.addCallback(this::onCloseClicked);
		addChild(closeRenderer);
		
		for (IRadialMenuOption option : allOptions)
		{
			RadialMenuItemRenderer renderer = new RadialMenuItemRenderer(option.getIconResource(), option.getText());
			addChild(renderer);

			ICallback<InteractableContainer> callback = adaptor.getCallback(option);
			renderer.addCallback(callback);

			optionButtonMap.put(option, renderer);

			renderers.add(renderer);
		}

		int len = renderers.size();
		for (int i = 0; i < len; i++)
		{
			float x = 200 * FloatMath.cos(2 * FloatMath.PI * i / len);
			float y = 200 * FloatMath.sin(2 * FloatMath.PI * i / len);

			RadialMenuItemRenderer renderer = renderers.get(i);
			renderer.setPosition(true, x, y);
		}
	}
	
	public void showFor(ArrayList<? extends IRadialMenuOption> enabledOptions)
	{
		visible = true;

		for (IRadialMenuOption option : allOptions)
		{
			RadialMenuItemRenderer button = optionButtonMap.get(option);
			boolean isEnabled = enabledOptions.contains(option);
			button.setEnabled(isEnabled);
		}
	}
	
	private void onCloseClicked(InteractableContainer data)
	{
		visible = false;
	}

	@Override
	protected void onDispose()
	{
		if (adaptor != null)
		{
			adaptor.dispose();
			adaptor = null;
		}
		
		if(optionButtonMap != null)
		{
			optionButtonMap.clear();
			optionButtonMap = null;
		}
		
		if(renderers != null)
		{
			renderers.clear();
			renderers = null;
		}
		
		allOptions = null;
	}
}

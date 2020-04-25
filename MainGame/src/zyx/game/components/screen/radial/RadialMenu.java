package zyx.game.components.screen.radial;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.screen.json.JsonSprite;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.utils.FloatMath;

public class RadialMenu extends JsonSprite
{

	private HashMap<InteractionAction, RadialMenuItemRenderer> actionButtonMap;
	private RadialButtonClickAdaptor adaptor;
	private ArrayList<RadialMenuItemRenderer> renderers;

	public RadialMenu()
	{
	}

	@Override
	protected void onPreInitialize()
	{
		actionButtonMap = new HashMap<>();
		adaptor = new RadialButtonClickAdaptor(this);
		renderers = new ArrayList<>();
	}
	
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
		String postFixButton = "_button";
		InteractionAction[] values = InteractionAction.values();
		for (InteractionAction value : values)
		{
			RadialMenuItemRenderer renderer = getComponentByName(value.name + postFixButton);

			ICallback<InteractableContainer> callback = adaptor.getCallback(value);
			renderer.addCallback(callback);

			actionButtonMap.put(value, renderer);

			if (value != InteractionAction.CLOSE)
			{
				renderers.add(renderer);
			}
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

	public void showFor(ArrayList<InteractionAction> enabledActions)
	{
		visible = true;

		InteractionAction[] allACtions = InteractionAction.values();
		for (InteractionAction value : allACtions)
		{
			RadialMenuItemRenderer button = actionButtonMap.get(value);
			boolean isEnabled = enabledActions.contains(value);
			button.setEnabled(isEnabled);
		}
	}

	@Override
	protected void onDispose()
	{
		if (adaptor != null)
		{
			adaptor.dispose();
			adaptor = null;
		}
	}

}

package zyx.game.components.screen.radial;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.components.screen.composed.ComposedButtonColorMap;
import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.screen.json.JsonSprite;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.utils.FloatMath;

public class RadialMenu extends JsonSprite
{

	private HashMap<InteractionAction, Button> actionButtonMap;
	private HashMap<InteractionAction, Image> actionIconMap;
	private RadialButtonClickAdaptor adaptor;
	private ArrayList<Button> buttons;
	private ArrayList<Image> buttonIcons;

	private Button closeButton;

	public RadialMenu()
	{
	}

	@Override
	public String getResource()
	{
		return "json.interaction.radial_menu";
	}

	@Override
	protected void onComponentsCreated()
	{
		actionButtonMap = new HashMap<>();
		actionIconMap = new HashMap<>();
		adaptor = new RadialButtonClickAdaptor(this);
		buttons = new ArrayList<>();
		buttonIcons = new ArrayList<>();

		String postFixButton = "_button";
		String postFixImage = "_icon";
		InteractionAction[] values = InteractionAction.values();
		for (InteractionAction value : values)
		{
			Button button = getComponentByName(value.name + postFixButton);
			Image icon = getComponentByName(value.name + postFixImage);

			ICallback<InteractableContainer> callback = adaptor.getCallback(value);
			button.onButtonClicked.addCallback(callback);
			actionButtonMap.put(value, button);
			actionIconMap.put(value, icon);
			
			if (value == InteractionAction.CLOSE)
			{
				closeButton = button;
			}
			else
			{
				buttons.add(button);
				buttonIcons.add(icon);
			}
		}

		int len = buttons.size();
		for (int i = 0; i < len; i++)
		{
			float x = 200 * FloatMath.cos(2 * FloatMath.PI * i / len);
			float y = 200 * FloatMath.sin(2 * FloatMath.PI * i / len);

			Button button = buttons.get(i);
			button.setPosition(true, x, y);
		}
	}

	public void showFor(ArrayList<InteractionAction> enabledActions)
	{
		ComposedButtonColorMap colorsEnabled = ComposedConstants.buttonColorsFromScheme("green");
		ComposedButtonColorMap colorsDisabled = ComposedConstants.buttonColorsFromScheme("gray");
		
		visible = true;

		InteractionAction[] allACtions = InteractionAction.values();
		for (InteractionAction value : allACtions)
		{
			Button button = actionButtonMap.get(value);
			Image icon = actionIconMap.get(value);
			if (enabledActions.contains(value))
			{
				button.focusable = true;
				button.setColors(colorsEnabled);
				icon.load("icon_" + value.name);
			}
			else
			{
				button.focusable = false;
				button.setColors(colorsDisabled);
				icon.load("icon_" + value.name + "_gray");
			}
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

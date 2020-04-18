package zyx.game.components.screen.radial;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.screen.json.JsonSprite;
import zyx.game.components.world.interactable.InteractionAction;

public class RadialMenu extends JsonSprite
{
	private HashMap<InteractionAction, Button> actionButtonMap;
	private RadialButtonClickAdaptor adaptor;
	private ArrayList<Button> buttons;

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
		adaptor = new RadialButtonClickAdaptor(this);
		buttons = new ArrayList<>();
		
		String postFix = "_button";
		InteractionAction[] values = InteractionAction.values();
		for (InteractionAction value : values)
		{
			Button button = getComponentByName(value.name + postFix);
			buttons.add(button);
			
			ICallback<InteractableContainer> callback = adaptor.getCallback(value);
			button.onButtonClicked.addCallback(callback);
			actionButtonMap.put(value, button);
		}
	}

	public void showFor(ArrayList<InteractionAction> values)
	{
		for (Button button : buttons)
		{
			button.visible = false;
		}
		
		visible = true;
		
		int xPos = 0;
		for (InteractionAction value : values)
		{
			Button button = actionButtonMap.get(value);
			if (button != null)
			{
				button.visible = true;
				button.setX(xPos);
				xPos += 64;
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

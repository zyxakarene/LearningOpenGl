package zyx.game.components.screen.hud;

import java.util.ArrayList;
import zyx.game.components.screen.radial.RadialMenu;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.controls.input.MouseData;

public class DinerHud extends BaseHud
{

	private RadialMenu interactionRadialMenu;
	private RadialMenu foodRadialMenu;

	public DinerHud()
	{
	}

	@Override
	public String getResource()
	{
		return "json.hud.diner_hud";
	}

	@Override
	protected String[] getDependencies()
	{
		return new String[]
		{
			"json.interaction.radial_menu"
		};
	}

	@Override
	protected void onComponentsCreated()
	{
		super.onComponentsCreated();

		interactionRadialMenu = this.<RadialMenu>getComponentByName("interaction_radial_menu");
		foodRadialMenu = this.<RadialMenu>getComponentByName("food_radial_menu");
	}
	
	public void showInteractions(ArrayList<InteractionAction> options)
	{
		interactionRadialMenu.showFor(options);
	}
	
	public void showFood()
	{
		foodRadialMenu.showAll();
		
		int x = MouseData.data.x;
		int y = MouseData.data.y;
		foodRadialMenu.setPosition(true, x, y);
	}
}

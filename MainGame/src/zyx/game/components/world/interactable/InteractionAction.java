package zyx.game.components.world.interactable;

import zyx.game.components.screen.radial.IRadialMenuOption;

public enum InteractionAction implements IRadialMenuOption
{
	TAKE("take", "Take"),
	PLACE("place", "Place"),
	SERVE("serve", "Serve"),
	TAKE_ORDER("take_order", "Take Order"),
	SERVE_BILL("serve_bill", "Serve Bill"),
	ADD_ORDER("add_order", "Add Order"),
	PRINT_BILL("print_bill", "Print Bill"),
	CLEANUP("cleanup", "Clean");
	
	public final String name;
	public final String text;

	private InteractionAction(String name, String text)
	{
		this.name = name;
		this.text = text;
	}

	@Override
	public String getText()
	{
		return text;
	}

	@Override
	public String getIconResource()
	{
		return String.format("icon_%s", name);
	}
}

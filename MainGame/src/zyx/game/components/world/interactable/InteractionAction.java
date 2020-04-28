package zyx.game.components.world.interactable;

import zyx.game.components.screen.radial.IRadialMenuOption;

public enum InteractionAction implements IRadialMenuOption
{
	TAKE(1, "take", "Take"),
	PLACE(2, "place", "Place"),
	SERVE(3, "serve", "Serve"),
	TAKE_ORDER(4, "take_order", "Take Order"),
	SERVE_BILL(5, "serve_bill", "Serve Bill"),
	ADD_ORDER(6, "add_order", "Add Order"),
	PRINT_BILL(7, "print_bill", "Print Bill"),
	CLEANUP(8, "cleanup", "Clean");
	
	public final int id;
	public final String name;
	public final String text;

	private InteractionAction(int id, String name, String text)
	{
		this.id = id;
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

	@Override
	public int getUniqueId()
	{
		return id;
	}
}

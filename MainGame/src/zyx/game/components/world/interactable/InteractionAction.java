package zyx.game.components.world.interactable;

import zyx.game.components.screen.radial.IRadialMenuOption;

public enum InteractionAction implements IRadialMenuOption
{
	CLOSE("close", true),
	TAKE("take"),
	PLACE("place"),
	SERVE("serve"),
	TAKE_ORDER("take_order"),
	SERVE_BILL("serve_bill"),
	ADD_ORDER("add_order"),
	PRINT_BILL("print_bill"),
	CLEANUP("cleanup");
	
	public final String name;
	public boolean isClose;

	private InteractionAction(String name)
	{
		this(name, false);
	}

	private InteractionAction(String name, boolean isClose)
	{
		this.name = name;
		this.isClose = isClose;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public boolean isCloseOption()
	{
		return isClose;
	}
}

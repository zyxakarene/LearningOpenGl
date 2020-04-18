package zyx.game.components.world.interactable;

public enum InteractionAction
{
	CLOSE("close"),
	TAKE("take"),
	PLACE("place"),
	SERVE("serve"),
	TAKE_ORDER("take_order"),
	SERVE_BILL("serve_bill"),
	ADD_ORDER("add_order"),
	PRINT_BILL("print_bill"),
	CLEANUP("cleanup");
	
	public final String name;

	private InteractionAction(String name)
	{
		this.name = name;
	}
}

package zyx.game.components.world.furniture;

public class Stove extends BaseFurnitureItem
{

	public Stove()
	{
		super(false);
		
		load("mesh.furniture.stove");
	}

	@Override
	protected void onGotItem()
	{
		view.addChildAsAttachment(item, "Oven_plate");
	}

	@Override
	protected void onLostItem()
	{
		view.removeChildAsAttachment(item);
	}
}

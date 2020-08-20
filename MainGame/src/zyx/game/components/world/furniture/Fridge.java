package zyx.game.components.world.furniture;

import zyx.game.components.AnimatedMesh;

public class Fridge extends NpcFurniture<AnimatedMesh>
{

	public Fridge()
	{
		super(true);
	}
	
	@Override
	public void interactWith(boolean started)
	{
		if (started)
		{
			view.setAnimation("open");
		}
		else
		{
			view.setAnimation("close");
		}
	}
	
	@Override
	protected String getResource()
	{
		return "mesh.furniture.fridge";
	}
}

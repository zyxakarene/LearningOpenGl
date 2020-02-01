package zyx.game.components.world.furniture;

import zyx.game.components.AnimatedMesh;

public class Fridge extends BaseFurnitureItem<AnimatedMesh>
{

	public Fridge()
	{
		super(true);
	}
	
	public void open()
	{
		view.setAnimation("open");
	}

	@Override
	protected String getResource()
	{
		return "mesh.furniture.fridge";
	}
}

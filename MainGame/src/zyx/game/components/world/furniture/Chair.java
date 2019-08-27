package zyx.game.components.world.furniture;

import zyx.game.components.SimpleMesh;

public class Chair extends BaseFurnitureItem<SimpleMesh>
{

	public Chair()
	{
		super(false);
	}
	
	@Override
	protected String getResource()
	{
		return "mesh.furniture.chair";
	}

}

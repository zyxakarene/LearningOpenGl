package zyx.game.components.world.furniture;

import zyx.game.components.SimpleMesh;

public class OrderMachine extends BaseFurnitureItem<SimpleMesh>
{

	public OrderMachine()
	{
		super(false);
	}
	
	@Override
	protected String getResource()
	{
		return "mesh.furniture.order_machine";
	}

}

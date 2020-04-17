package zyx.game.components.world.furniture;

import zyx.game.components.SimpleMesh;

public class Monitor extends NpcFurniture<SimpleMesh>
{

	public Monitor()
	{
		super(false);
	}
	
	@Override
	protected String getResource()
	{
		return "mesh.furniture.monitor";
	}
}

package zyx.game.components.world.furniture;

import zyx.engine.components.world.World3D;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.GameItem;
import zyx.utils.GeometryUtils;

public class Floor extends BaseFurnitureItem<SimpleMesh>
{

	public Floor()
	{
		super(false);
	}

	@Override
	protected String getResource()
	{
		return "mesh.box";
	}

	@Override
	protected void onGotItem(GameItem item)
	{
		World3D.instance.addChild(item);
		item.setDir(true, GeometryUtils.ROTATION_X);
	}

	@Override
	protected void onLostItem(GameItem item)
	{
		World3D.instance.removeChild(item);
	}
}

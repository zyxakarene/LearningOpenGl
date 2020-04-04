package zyx.engine.utils.worldpicker;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.utils.interfaces.IPhysbox;

public final class ClickedInfo
{
	public WorldObject worldObject;
	public IPhysbox target;
	public Vector3f position;
}

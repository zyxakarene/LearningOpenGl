package zyx.engine.utils.worldpicker;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.components.GameObject;
import zyx.utils.interfaces.IPhysbox;

public final class ClickedInfo
{
	public GameObject gameObject;
	public IPhysbox target;
	public Vector3f position;
}

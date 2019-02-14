package zyx.engine.utils.worldpicker;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.physics.PhysTriangle;

public class ColliderInfo
{
	public final PhysTriangle triangle = new PhysTriangle();
	public final Vector3f intersectPoint = new Vector3f();
	
	public float triangleAngle;
	public boolean hasCollision;
}

package zyx.game.controls;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.pooling.GenericPool;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableMatrix4f;
import zyx.utils.pooling.model.PoolableVector3f;

public class SharedPools
{
	public static final ObjectPool<Vector3f> VECTOR_POOL = new GenericPool(PoolableVector3f.class, 50);
	public static final ObjectPool<Matrix4f> MATRIX_POOL = new GenericPool(PoolableMatrix4f.class, 10);
}

package zyx.game.controls;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.pooling.GenericPool;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableMatrix4f;
import zyx.utils.pooling.model.PoolableQuaternion;
import zyx.utils.pooling.model.PoolableVector2f;
import zyx.utils.pooling.model.PoolableVector3f;

public class SharedPools
{
	public static final ObjectPool<Vector2f> VECTOR_POOL_2F = new GenericPool(PoolableVector2f.class, 0);
	public static final ObjectPool<Vector3f> VECTOR_POOL = new GenericPool(PoolableVector3f.class, 0);
	public static final ObjectPool<Matrix4f> MATRIX_POOL = new GenericPool(PoolableMatrix4f.class, 0);
	public static final ObjectPool<Quaternion> QUARERNION_POOL = new GenericPool(PoolableQuaternion.class, 0);
}

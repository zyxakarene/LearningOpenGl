package zyx.utils.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class DecomposedMatrix
{
	private static final Matrix4f CLONE = new Matrix4f();
	private static final Vector3f HELPER_POS = new Vector3f();
	private static final Vector3f HELPER_SCALE = new Vector3f();
	private static final Quaternion HELPER_QUAT = new Quaternion();
		
	private Matrix4f source;
	
	public Vector3f position = new Vector3f();
	public Vector3f scale = new Vector3f();
	public Vector3f rotation = new Vector3f();

	public DecomposedMatrix(Matrix4f source)
	{
		setSource(source);
	}
	
	public final void setSource(Matrix4f source)
	{
		this.source = source;
		
		MatrixUtils.getPositionFrom(source, position);
		MatrixUtils.getScaleFrom(source, scale);
		
		CLONE.load(source);
		
		HELPER_POS.x = -position.x;
		HELPER_POS.y = -position.y;
		HELPER_POS.z = -position.z;
		
		HELPER_SCALE.x = 1 / scale.x;
		HELPER_SCALE.y = 1 / scale.y;
		HELPER_SCALE.z = 1 / scale.z;
		
		CLONE.translate(HELPER_POS);
		CLONE.scale(HELPER_SCALE);
		
		HELPER_QUAT.setFromMatrix(CLONE);
		HELPER_QUAT.negate();
		
		QuaternionUtils.toRad(HELPER_QUAT, rotation);
		
		rotation.x *= FloatMath.RAD_TO_DEG;
		rotation.y *= FloatMath.RAD_TO_DEG;
		rotation.z *= FloatMath.RAD_TO_DEG;
	}
	
	public Matrix4f recompose()
	{
		source.setIdentity();
		

		source.translate(position);
		
		source.rotate(FloatMath.toRadians(rotation.z), GeometryUtils.ROTATION_Z);
		source.rotate(FloatMath.toRadians(rotation.y), GeometryUtils.ROTATION_Y);
		source.rotate(FloatMath.toRadians(rotation.x), GeometryUtils.ROTATION_X);
		
		source.scale(scale);
		
		return source;
	}
}

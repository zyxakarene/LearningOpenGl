package zyx.engine.components.world;

import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.utils.ScreenSize;
import zyx.game.controls.lights.LightsManager;
import zyx.opengl.camera.Projection;
import zyx.opengl.lighs.ISun;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.GeometryUtils;

public class GameSun extends WorldObject implements ISun
{

	private static final Vector3f HELPER_VEC = new Vector3f();
	private static final Vector4f HELPER_WORLD_SPACE = new Vector4f();

	private boolean dirtyPos;

	private Matrix4f invertedCamera;
	private Vector3f globalLightDir;

	private final int cascadeCount = 4;
	private float[] cascadeLimits = new float[cascadeCount + 1];

	private Vector4f[] frustumCornersL;
	private HashMap<Integer, Vector4f[]> frustumCornerMap;
	private Matrix4f[] cascadeToMatrixMap;

	GameSun()
	{
		globalLightDir = new Vector3f();
		
		invertedCamera = new Matrix4f();
		frustumCornersL = new Vector4f[8];
		frustumCornerMap = new HashMap<>();
		
		cascadeToMatrixMap = new Matrix4f[cascadeCount];
		cascadeToMatrixMap[0] = SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION_CASCADE_1;
		cascadeToMatrixMap[1] = SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION_CASCADE_2;
		cascadeToMatrixMap[2] = SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION_CASCADE_3;
		cascadeToMatrixMap[3] = SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION_CASCADE_4;

		for (int i = 0; i < frustumCornersL.length; i++)
		{
			frustumCornersL[i] = new Vector4f();
		}

		float[] initialCascadeLimits = new float[]
		{
			1f,
			50,
			100f,
			180f,
			350f
		};
		setCascades(initialCascadeLimits);
		LightsManager.getInstane().setSun(this);
	}

	void setEnabled(boolean enabled)
	{
		if (enabled)
		{
			LightsManager.getInstane().setSun(this);
		}
		else
		{
			LightsManager.getInstane().setSun(null);
		}
	}

	void setCascades(float[] cascades)
	{
		if (cascades.length != cascadeLimits.length)
		{
			throw new RuntimeException("Invalid cascade count. Must be " + cascadeLimits.length);
		}

		for (int i = 0; i < cascades.length; i++)
		{
			cascadeLimits[i] = -cascades[i];
		}

		float ar = (float) ScreenSize.height / (float) ScreenSize.width;
		float tanHalfHFOV = FloatMath.tan(FloatMath.DEG_TO_RAD * (GameConstants.FOV / 2f));
		float tanHalfVFOV = FloatMath.tan(FloatMath.DEG_TO_RAD * ((GameConstants.FOV * ar) / 2f));

		tanHalfHFOV *= 3f;
		tanHalfVFOV *= 3f;

		for (int i = 0; i < cascadeCount; i++)
		{
			float xn = cascadeLimits[i] * tanHalfHFOV;
			float xf = cascadeLimits[i + 1] * tanHalfHFOV;
			float yn = cascadeLimits[i] * tanHalfVFOV;
			float yf = cascadeLimits[i + 1] * tanHalfVFOV;

			Vector4f[] frustumCorners = frustumCornerMap.get(i);
			if (frustumCorners == null)
			{
				frustumCorners = createFrustumCorners(i);
			}

			// near face
			frustumCorners[0].set(xn, yn, cascadeLimits[i], 1);
			frustumCorners[1].set(-xn, yn, cascadeLimits[i], 1);
			frustumCorners[2].set(xn, -yn, cascadeLimits[i], 1);
			frustumCorners[3].set(-xn, -yn, cascadeLimits[i], 1);
			// far face
			frustumCorners[4].set(xf, yf, cascadeLimits[i + 1], 1);
			frustumCorners[5].set(-xf, yf, cascadeLimits[i + 1], 1);
			frustumCorners[6].set(xf, -yf, cascadeLimits[i + 1], 1);
			frustumCorners[7].set(-xf, -yf, cascadeLimits[i + 1], 1);
		}
	}
	
	private Vector4f[] createFrustumCorners(int id)
	{
		Vector4f[] corners = new Vector4f[8];
		for (int i = 0; i < corners.length; i++)
		{
			corners[i] = new Vector4f();
		}
		
		frustumCornerMap.put(id, corners);
		
		return corners;
	}
	
	@Override
	protected void updateTransforms(boolean alsoChildren)
	{
		super.updateTransforms(alsoChildren);

		dirtyPos = true;
	}

	@Override
	public void getSunDirection(Vector3f out)
	{
		out.set(globalLightDir);
	}

	@Override
	public void calculateShadows()
	{
		if (dirtyPos)
		{
			setRotation();
			dirtyPos = false;
		}

		setSunPosDynamic();
	}

	private void setSunPosDynamic()
	{
		Matrix4f cam = SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM;
		Matrix4f.invert(cam, invertedCamera);

		Matrix4f LightM = SharedShaderObjects.SUN_VIEW_TRANSFORM;

		for (int i = 0; i < cascadeCount; i++)
		{
			Vector4f[] frustumCorners = frustumCornerMap.get(i);

			float minX = Float.MAX_VALUE;
			float maxX = -Float.MAX_VALUE;
			float minY = Float.MAX_VALUE;
			float maxY = -Float.MAX_VALUE;
			float minZ = Float.MAX_VALUE;
			float maxZ = -Float.MAX_VALUE;

			for (int j = 0; j < frustumCorners.length; j++)
			{
				// Transform the frustum coordinate from view to world space
				Matrix4f.transform(invertedCamera, frustumCorners[j], HELPER_WORLD_SPACE);

				// Transform the frustum coordinate from world to light space
				Matrix4f.transform(LightM, HELPER_WORLD_SPACE, frustumCornersL[j]);

				minX = FloatMath.min(minX, frustumCornersL[j].x);
				maxX = FloatMath.max(maxX, frustumCornersL[j].x);
				minY = FloatMath.min(minY, frustumCornersL[j].y);
				maxY = FloatMath.max(maxY, frustumCornersL[j].y);
				minZ = FloatMath.min(minZ, frustumCornersL[j].z);
				maxZ = FloatMath.max(maxZ, frustumCornersL[j].z);
			}

			maxZ += 1000f;
			minZ -= 1000f;

			float r = maxX;
			float l = minX;
			float b = minY;
			float t = maxY;
			float f = maxZ;
			float n = minZ;

			Matrix4f mat = cascadeToMatrixMap[i];
			Projection.createOrthographic(n, f, l, r, t, b, mat);
		}

		SharedShaderObjects.combineMatrices();
	}

	private void setRotation()
	{
		getRotation(false, HELPER_VEC);
		float x = FloatMath.toRadians(HELPER_VEC.x);
		float y = FloatMath.toRadians(HELPER_VEC.y);
		float z = FloatMath.toRadians(HELPER_VEC.z);

		Matrix4f viewMatrix = SharedShaderObjects.SUN_VIEW_TRANSFORM;
		viewMatrix.setIdentity();
		viewMatrix.rotate(x, GeometryUtils.ROTATION_X);
		viewMatrix.rotate(y, GeometryUtils.ROTATION_Y);
		viewMatrix.rotate(z, GeometryUtils.ROTATION_Z);
		
		globalLightDir.set(viewMatrix.m02, viewMatrix.m12, viewMatrix.m22);
	}
}

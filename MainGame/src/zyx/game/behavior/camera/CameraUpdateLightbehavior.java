package zyx.game.behavior.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.GLUtils;
import zyx.opengl.camera.Camera;
import zyx.opengl.camera.Projection;
import zyx.opengl.camera.ViewFrustum;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.DepthShader;
import zyx.opengl.shaders.implementations.LightingPassShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.GeometryUtils;
import zyx.utils.cheats.DebugPoint;
import zyx.utils.cheats.Print;

public class CameraUpdateLightbehavior extends Behavior
{

	private DepthShader depthShader;
	private LightingPassShader lightShader;
	private WorldShader worldShader;
	private Vector3f cameraRot;
	private CameraController controller;

	public CameraUpdateLightbehavior()
	{
		super(BehaviorType.CAMERA_LIGHT);
	}

	@Override
	public void initialize()
	{
		cameraRot = new Vector3f();
		depthShader = (DepthShader) ShaderManager.INSTANCE.get(Shader.DEPTH);
		lightShader = (LightingPassShader) ShaderManager.INSTANCE.get(Shader.DEFERED_LIGHT_PASS);
		worldShader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);
		controller = (CameraController) gameObject;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (KeyboardData.data.wasPressed(Keyboard.KEY_E))
		{
			controller.getRotation(true, cameraRot);

			float elevation = FloatMath.toRadians(cameraRot.z);
			float heading = FloatMath.toRadians(cameraRot.x);
			HELPER_DIR.x = FloatMath.cos(elevation) * FloatMath.sin(heading);

			elevation = FloatMath.toRadians(cameraRot.y);
			heading = FloatMath.toRadians(cameraRot.x);
			HELPER_DIR.z = FloatMath.cos(elevation) * FloatMath.cos(heading);

			elevation = FloatMath.toRadians(cameraRot.y);
			heading = FloatMath.toRadians(cameraRot.x);
			HELPER_DIR.y = FloatMath.cos(elevation) * FloatMath.sin(elevation);

			Matrix4f mat = new Matrix4f();
			mat.translate(gameObject.getPosition(false, null));
			mat.rotate(FloatMath.toRadians(cameraRot.x), GeometryUtils.ROTATION_X);
			mat.rotate(FloatMath.toRadians(cameraRot.y), GeometryUtils.ROTATION_Y);
			mat.rotate(FloatMath.toRadians(cameraRot.z), GeometryUtils.ROTATION_Z);

			Vector3f a = new Vector3f(mat.m20, mat.m21, mat.m22); // Left-handed column oriented: get z row.
			Vector3f b = new Vector3f(mat.m02, mat.m12, mat.m22); // Left-handed row oriented: get z column.
			Vector3f c = new Vector3f(-mat.m20, -mat.m21, -mat.m22); // OpenGL style matrix (right-handed, column oriented), get the -z row.
			Vector3f d = new Vector3f(mat.m02, mat.m12, mat.m22); // Right-handed row oriented: get -z column.

			setSunShadowDir();
			setSunPos();

			GLUtils.errorCheck();
			lightShader.uploadLightDirection(d);
			GLUtils.errorCheck();
		}

//		setSunPos();
		setSunPosDynamic();
	}

	private int shadowToUse = 0;
	private Vector4f cameraRotationRad = new Vector4f();
	private Vector3f cameraDir = new Vector3f();

	protected void setSunShadowDir()
	{
		Vector3f cameraRotation = gameObject.getRotation(false, null);

		cameraRotationRad.x = FloatMath.toRadians(cameraRotation.x);
		cameraRotationRad.y = FloatMath.toRadians(cameraRotation.y);
		cameraRotationRad.z = FloatMath.toRadians(cameraRotation.z);
		cameraRotationRad.w = FloatMath.toRadians(cameraRotation.x + 90);

		ViewFrustum fru = new ViewFrustum();
		fru.extractPlanesFrom(SharedShaderObjects.WORLD_PROJECTION_VIEW_TRANSFORM);
		fru.near.d = -100000f;
		depthShader.uploadSunNearPlane(fru.near);
		
		
		gameObject.getDir(false, cameraDir);
	}

	private void setSunPosDynamic()
	{
		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			DebugPoint.clearAll();
		}
		
		Matrix4f cam = new Matrix4f();
		Matrix4f camInv = new Matrix4f();
		
		Camera.getInstance().getViewMatrix(cam);
		Matrix4f.invert(cam, camInv);

		Matrix4f LightM = new Matrix4f();
		LightM.rotate(cameraRotationRad.x, GeometryUtils.ROTATION_X);
		LightM.rotate(cameraRotationRad.y, GeometryUtils.ROTATION_Y);
		LightM.rotate(cameraRotationRad.z, GeometryUtils.ROTATION_Z);

		float ar = (float) GameConstants.GAME_HEIGHT / (float) GameConstants.GAME_WIDTH;
		float tanHalfHFOV = FloatMath.tan(FloatMath.DEG_TO_RAD * (GameConstants.FOV / 2f));
		float tanHalfVFOV = FloatMath.tan(FloatMath.DEG_TO_RAD * ((GameConstants.FOV * ar) / 2f));
		
		tanHalfHFOV *= 3f;
		tanHalfVFOV *= 3f;

		float[] m_cascadeEnd = new float[]
		{
			-1f,
			-60,
			-100f,
			-150f
		};

		for (int i = 0; i < m_cascadeEnd.length; i++)
		{
			m_cascadeEnd[i] = m_cascadeEnd[i] * 0.5f;
		}
		
		int cascadeCount = m_cascadeEnd.length - 1;
		for (int i = 0; i < cascadeCount; i++)
		{
			float xn = m_cascadeEnd[i] * tanHalfHFOV;
			float xf = m_cascadeEnd[i + 1] * tanHalfHFOV;
			float yn = m_cascadeEnd[i] * tanHalfVFOV;
			float yf = m_cascadeEnd[i + 1] * tanHalfVFOV;

			Vector4f[] frustumCorners = new Vector4f[]
			{
				// near face
				new Vector4f(xn, yn, m_cascadeEnd[i], 1),
				new Vector4f(-xn, yn, m_cascadeEnd[i], 1),
				new Vector4f(xn, -yn, m_cascadeEnd[i], 1),
				new Vector4f(-xn, -yn, m_cascadeEnd[i], 1),
				// far face
				new Vector4f(xf, yf, m_cascadeEnd[i + 1], 1),
				new Vector4f(-xf, yf, m_cascadeEnd[i + 1], 1),
				new Vector4f(xf, -yf, m_cascadeEnd[i + 1], 1),
				new Vector4f(-xf, -yf, m_cascadeEnd[i + 1], 1)
			};

			Vector4f[] frustumCornersL = new Vector4f[frustumCorners.length];
			for (int j = 0; j < frustumCornersL.length; j++)
			{
				frustumCornersL[j] = new Vector4f();
			}

			float minX = Float.MAX_VALUE;
			float maxX = -Float.MAX_VALUE;
			float minY = Float.MAX_VALUE;
			float maxY = -Float.MAX_VALUE;
			float minZ = Float.MAX_VALUE;
			float maxZ = -Float.MAX_VALUE;

			for (int j = 0; j < frustumCorners.length; j++)
			{
				// Transform the frustum coordinate from view to world space
				Vector4f vW = Matrix4f.transform(camInv, frustumCorners[j], null);

				if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
				{
					DebugPoint point = DebugPoint.addToScene(vW, 0);
					point.setScale(1f, 1f, 1f);
				}
				
				// Transform the frustum coordinate from world to light space
				Matrix4f.transform(LightM, vW, frustumCornersL[j]);

				if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE) && i == shadowToUse)
				{
//					DebugPoint.addToScene(frustumCornersL[j], 0);
				}
				
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
			Matrix4f ortho = new Matrix4f();
			
			if (i == shadowToUse)
			{
				if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
				{
					maxZ -= 1000f;
					minZ += 100f;
					
//					DebugPoint.addToScene(maxX, maxY, minZ, 0);
//					DebugPoint.addToScene(minX, maxY, minZ, 0);
//					DebugPoint.addToScene(maxX, minY, minZ, 0);
//					DebugPoint.addToScene(minX, minY, minZ, 0);
//					
//					DebugPoint.addToScene(maxX, maxY, maxZ, 0);
//					DebugPoint.addToScene(minX, maxY, maxZ, 0);
//					DebugPoint.addToScene(maxX, minY, maxZ, 0);
//					DebugPoint.addToScene(minX, minY, maxZ, 0);
				}
				
				Projection.createOrthographic(n, f, l, r, t, b, ortho);
				SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION.load(ortho);
			}
		}
		
		SharedShaderObjects.combineMatrices();
		
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_UP))
		{
			shadowToUse++;
			if (shadowToUse >= 3)
			{
				shadowToUse = 0;
			}
		}
		
		lightShader.uploadSunMatrix();
	}

	private void setSunPos()
	{
		Matrix4f viewMatrix = SharedShaderObjects.SUN_VIEW_TRANSFORM;
		viewMatrix.setIdentity();
		viewMatrix.rotate(cameraRotationRad.x, GeometryUtils.ROTATION_X);
		viewMatrix.rotate(cameraRotationRad.y, GeometryUtils.ROTATION_Y);
		viewMatrix.rotate(cameraRotationRad.z, GeometryUtils.ROTATION_Z);
		
		SharedShaderObjects.combineMatrices();

		lightShader.uploadSunMatrix();
	}

}

package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.utils.ScreenSize;
import zyx.opengl.camera.Camera;

public class RayPicker
{
	private static Vector4f HELPER_VECTOR = new Vector4f();

	private static RayPicker INSTANCE = new RayPicker();

	private Camera camera;
	private Matrix4f inverseProjection;
	private Matrix4f inverseView;

	private Vector3f currentRay;
	
	private int lastX;
	private int lastY;

	private RayPicker()
	{
		inverseProjection = new Matrix4f();
		inverseView = new Matrix4f();
		currentRay = new Vector3f();

		camera = Camera.getInstance();
	}

	public void setProjectionMatrix(Matrix4f matrix)
	{
		inverseProjection.load(matrix);
		inverseProjection.invert();
	}
	
	public static RayPicker getInstance()
	{
		return INSTANCE;
	}

	public Vector3f getRay()
	{
		return currentRay;
	}

	public void updateMousePos(int x, int y)
	{
		if(x != lastX || y != lastY)
		{
			lastX = x;
			lastY = y;
			
			camera.getViewMatrix(inverseView);
			inverseView.invert();

			calculateMouseRay(x, y);
		}
	}

	private void calculateMouseRay(int x, int y)
	{
		getClipCoords(x, y, HELPER_VECTOR);
		getEyeCoords(HELPER_VECTOR);
		getWorldCoords(HELPER_VECTOR, currentRay);
		
		currentRay.normalise();
	}

	private void getClipCoords(int x, int y, Vector4f out)
	{
		float deviceX = (2.0f * x) / ScreenSize.width - 1f;
		float deviceY = 1f - (2.0f * y) / ScreenSize.height;
		
		out.set(deviceX, deviceY, -1, 1);
	}
	
	private void getEyeCoords(Vector4f out)
	{
		Matrix4f.transform(inverseProjection, out, out);
		out.z = -1;
		out.w = 0;
	}
	
	private void getWorldCoords(Vector4f input, Vector3f out)
	{
		Matrix4f.transform(inverseView, input, input);
		out.set(input.x, input.y, input.z);
	}
}

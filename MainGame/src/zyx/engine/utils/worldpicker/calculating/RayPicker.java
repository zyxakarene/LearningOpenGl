package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.components.screen.base.Stage;
import zyx.engine.components.screen.base.docks.DockType;
import zyx.game.controls.input.MouseData;
import zyx.opengl.camera.Camera;
import zyx.utils.cheats.Print;
import zyx.utils.geometry.IntRectangle;

public class RayPicker
{
	private static Vector4f HELPER_VECTOR = new Vector4f();

	private static RayPicker INSTANCE = new RayPicker();

	private Camera camera;
	private Matrix4f inverseProjection;
	private Matrix4f inverseView;
	private MouseData mouseData;
	private IntRectangle dockSize;

	private RayPickerRay currentRay;

	private RayPicker()
	{
		inverseProjection = new Matrix4f();
		inverseView = new Matrix4f();
		currentRay = new RayPickerRay();
		dockSize = new IntRectangle();

		camera = Camera.getInstance();
		mouseData = MouseData.data;
	}

	public void setProjectionMatrix(Matrix4f matrix)
	{
		inverseProjection.load(matrix);
		inverseProjection.invert();
		
		Stage.getInstance().getDockSize(dockSize, DockType.Top);
	}
	
	public static RayPicker getInstance()
	{
		return INSTANCE;
	}

	public RayPickerRay getRay()
	{
		return currentRay;
	}
	
	public void updateMousePos(int x, int y)
	{
		if(mouseData.dX != 0 || mouseData.dY != 0)
		{
			x = x - dockSize.x;
			y = y - dockSize.y;
			
			currentRay.valid = !(x < 0 || y < 0 || x > dockSize.width || y > dockSize.height);

			if (currentRay.valid == false)
			{
				return;
			}
			
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
		float deviceX = (2.0f * x) / dockSize.width - 1f;
		float deviceY = 1f - (2.0f * y) / dockSize.height;
		
		out.set(deviceX, deviceY, -1, 1);
	}
	
	private void getEyeCoords(Vector4f out)
	{
		Matrix4f.transform(inverseProjection, out, out);
		out.z = -1;
		out.w = 0;
	}
	
	private void getWorldCoords(Vector4f input, RayPickerRay out)
	{
		Matrix4f.transform(inverseView, input, input);
		out.x = input.x;
		out.y = input.y;
		out.z = input.z;
	}
}

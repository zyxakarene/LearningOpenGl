package zyx.engine.utils;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.camera.Camera;
import zyx.utils.GameConstants;

public class RayPicker
{

	private static RayPicker INSTANCE = new RayPicker();

	private Camera camera;
	private Matrix4f inverseProjection;
	private Matrix4f inverseView;

	private Vector3f currentRay;
	private int lastPosX;
	private int lastPosY;

	private RayPicker()
	{
		inverseProjection = new Matrix4f();
		inverseView = new Matrix4f();
		currentRay = new Vector3f();

		lastPosX = 0;
		lastPosX = 0;

		camera = Camera.getInstance();
		camera.getProjectionMatrix(inverseProjection);
		camera.getViewMatrix(inverseView);
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
		int diffX = Math.abs(x - lastPosX);
		int diffY = Math.abs(y - lastPosY);

		if (diffX >= 1 || diffY >= 1)
		{
			lastPosX = x;
			lastPosY = y;

			calculateMouseRay();
		}
	}

	private void calculateMouseRay()
	{
		Vector2f normalizedCoords = getNormalisedDeviceCoordinates(lastPosX, lastPosY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);

		currentRay.set(worldRay);
	}

	private Vector3f toWorldCoords(Vector4f eyeCoords)
	{
		Vector4f rayWorld = Matrix4f.transform(inverseView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords)
	{
		Vector4f eyeCoords = Matrix4f.transform(inverseProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vector2f getNormalisedDeviceCoordinates(int mouseX, int mouseY)
	{
		float x = (2.0f * mouseX) / GameConstants.GAME_HEIGHT - 1f;
		float y = (2.0f * mouseY) / GameConstants.GAME_WIDTH - 1f;
		return new Vector2f(x, y);
	}

}

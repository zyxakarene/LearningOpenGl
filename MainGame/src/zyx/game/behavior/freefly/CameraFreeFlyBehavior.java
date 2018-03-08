package zyx.game.behavior.freefly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.MouseData;
import zyx.utils.DeltaTime;
import zyx.utils.FloatMath;

public class CameraFreeFlyBehavior extends Behavior
{

	private static final int FORWARD = 1;
	private static final int BACKWARD = 2;
	private static final int RIGHT = 3;
	private static final int LEFT = 4;
	
	private static final float MOVE_SPEED = 0.2f;
	
	private Vector3f cameraPosition;
	private Vector3f cameraRotation;

	public CameraFreeFlyBehavior()
	{
		super(BehaviorType.CAMERA_FREE_FLY);
	}

	@Override
	public void initialize()
	{		
		cameraPosition = new Vector3f();
		cameraRotation = new Vector3f();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		gameObject.getPosition(false, cameraPosition);
		gameObject.getRotation(false, cameraRotation);
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_Z))
		{
			Mouse.setGrabbed(!Mouse.isGrabbed());
		}

		if (Mouse.isGrabbed())
		{
			int dx = (int) (MouseData.instance.dX * DeltaTime.getDeltaVariant());
			int dy = (int) (MouseData.instance.dY * DeltaTime.getDeltaVariant());
			rotate(-dy, 0, dx, elapsedTime);
		}

		if (KeyboardData.data.isDown(Keyboard.KEY_W))
		{
			move(FORWARD, elapsedTime);
		}
		if (KeyboardData.data.isDown(Keyboard.KEY_S))
		{
			move(BACKWARD, elapsedTime);
		}

		if (KeyboardData.data.isDown(Keyboard.KEY_D))
		{
			move(RIGHT, elapsedTime);
		}
		if (KeyboardData.data.isDown(Keyboard.KEY_A))
		{
			move(LEFT, elapsedTime);
		}

	}

	private void rotate(int x, int y, int z, int elapsedTime)
	{
		float multiplier = elapsedTime * 0.008f;

        cameraRotation.x += (x * multiplier);
        cameraRotation.y += (y * multiplier);
        cameraRotation.z += (z * multiplier);

        if (cameraRotation.x > 0)
        {
            cameraRotation.x = 0;
        }
        else if (cameraRotation.x < -180)
        {
            cameraRotation.x = -180;
        }

        if (cameraRotation.z > 360)
        {
            cameraRotation.z = cameraRotation.z - 360;
        }
        else if (cameraRotation.z < 0)
        {
            cameraRotation.z = cameraRotation.z + 360;
        }
	}
	
	private void move(int direction, int elapsedTime)
	{
		float multiplier = elapsedTime * 0.15f;

        float dX = FloatMath.sin(FloatMath.toRadians(cameraRotation.z)) * FloatMath.cos(FloatMath.toRadians(cameraRotation.x + 90)) * MOVE_SPEED * multiplier;
        float dY = FloatMath.cos(FloatMath.toRadians(cameraRotation.z)) * FloatMath.cos(FloatMath.toRadians(cameraRotation.x + 90)) * MOVE_SPEED * multiplier;
        float dZ = FloatMath.cos(FloatMath.toRadians(cameraRotation.x)) * MOVE_SPEED * multiplier;

        switch (direction)
        {
            case (FORWARD):
            {
                cameraPosition.x -= dX;
                cameraPosition.y -= dY;
                cameraPosition.z += dZ;
                break;
            }
            case (BACKWARD):
            {
				cameraPosition.x += dX;
                cameraPosition.y += dY;
                cameraPosition.z -= dZ;
                break;
            }
            case (RIGHT):
            {
                dX = FloatMath.sin(FloatMath.toRadians(cameraRotation.z + 90)) * MOVE_SPEED * multiplier;
                dY = FloatMath.cos(FloatMath.toRadians(cameraRotation.z + 90)) * MOVE_SPEED * multiplier;

				cameraPosition.x -= dX;
                cameraPosition.y -= dY;
                break;
            }
            case (LEFT):
            {
                dX = FloatMath.sin(FloatMath.toRadians(cameraRotation.z - 90)) * MOVE_SPEED * multiplier;
                dY = FloatMath.cos(FloatMath.toRadians(cameraRotation.z - 90)) * MOVE_SPEED * multiplier;

				cameraPosition.x -= dX;
                cameraPosition.y -= dY;
                break;
            }
        }
	}

}

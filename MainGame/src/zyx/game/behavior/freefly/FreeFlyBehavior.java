package zyx.game.behavior.freefly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.InputManager;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.input.scheme.InputScheme;
import zyx.utils.DeltaTime;
import zyx.utils.FloatMath;
import zyx.utils.cheats.Print;

public class FreeFlyBehavior extends Behavior
{

	private static final int FORWARD = 1;
	private static final int STRAFE = 2;
	
	private static final float MOVE_SPEED = 0.2f;
	
	private Vector3f cameraPosition;
	private Vector3f cameraRotation;
	private InputScheme inputScheme;

	public FreeFlyBehavior()
	{
		super(BehaviorType.CAMERA_FREE_FLY);
	}

	@Override
	public void initialize()
	{		
		inputScheme = InputManager.getInstance().scheme;
		cameraPosition = new Vector3f();
		cameraRotation = new Vector3f();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		gameObject.getPosition(true, cameraPosition);
		gameObject.getRotation(true, cameraRotation);
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_Z))
		{
			Mouse.setGrabbed(!Mouse.isGrabbed());
		}

		if (Mouse.isGrabbed())
		{
			int dx = (int) (MouseData.data.dX * DeltaTime.getDeltaVariant());
			int dy = (int) (MouseData.data.dY * DeltaTime.getDeltaVariant());
			rotate(-dy, 0, dx, elapsedTime);
		}

		if (inputScheme.forward != 0)
		{
			move(FORWARD, inputScheme.forward, elapsedTime);
		}

		if (inputScheme.strafe != 0)
		{
			move(STRAFE, inputScheme.strafe, elapsedTime);
		}

		gameObject.setPosition(true, cameraPosition);
		gameObject.setRotation(cameraRotation);
	}

	private void rotate(int x, int y, int z, int elapsedTime)
	{
		float multiplier = elapsedTime * 0.008f;
		
        cameraRotation.x -= (x * multiplier);
        cameraRotation.y -= (y * multiplier);
        cameraRotation.z -= (z * multiplier);

        if (cameraRotation.x < 0)
        {
            cameraRotation.x = 0;
        }
        else if (cameraRotation.x > 179.99f)
        {
            cameraRotation.x = 179.99f;
        }

        if (cameraRotation.z < -360)
        {
            cameraRotation.z = cameraRotation.z + 360;
        }
        else if (cameraRotation.z < 0)
        {
            cameraRotation.z = cameraRotation.z - 360;
        }
	}
	
	private void move(int direction, float value, int elapsedTime)
	{
		float multiplier = value * elapsedTime * 0.15f * inputScheme.moveModifier;
		
        float dX = FloatMath.sin(FloatMath.toRadians(-cameraRotation.z)) * FloatMath.cos(FloatMath.toRadians(-cameraRotation.x + 90)) * MOVE_SPEED * multiplier;
        float dY = FloatMath.cos(FloatMath.toRadians(-cameraRotation.z)) * FloatMath.cos(FloatMath.toRadians(-cameraRotation.x + 90)) * MOVE_SPEED * multiplier;
        float dZ = FloatMath.cos(FloatMath.toRadians(-cameraRotation.x)) * MOVE_SPEED * multiplier;

        switch (direction)
        {
            case (FORWARD):
            {
                cameraPosition.x += dX;
                cameraPosition.y += dY;
                cameraPosition.z -= dZ;
                break;
            }
            case (STRAFE):
            {
                dX = FloatMath.sin(FloatMath.toRadians(-cameraRotation.z + 90)) * MOVE_SPEED * multiplier;
                dY = FloatMath.cos(FloatMath.toRadians(-cameraRotation.z + 90)) * MOVE_SPEED * multiplier;

				cameraPosition.x += dX;
                cameraPosition.y += dY;
                break;
            }
        }
	}

}

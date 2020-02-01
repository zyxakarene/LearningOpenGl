package zyx.game.behavior.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.Collider;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.camera.Camera;
import zyx.utils.FloatMath;

public class PlayerMovementBehavior extends Behavior
{

	private static final int FORWARD = 1;
	private static final int BACKWARD = 2;
	private static final int RIGHT = 3;
	private static final int LEFT = 4;

	private static final float MOVE_SPEED = 20f;

	private Collider collider;
	private KeyboardData keyboard;
	private Vector3f cameraRotation;

	public PlayerMovementBehavior()
	{
		super(BehaviorType.PLAYER_MOVEMENT);
	}

	@Override
	public void initialize()
	{
		collider = gameObject.getCollider();
		cameraRotation = new Vector3f();
		keyboard = KeyboardData.data;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (collider == null)
		{
			collider = gameObject.getCollider();
			return;
		}

		collider.velocity.x = 0;
		collider.velocity.y = 0;

		Camera.getInstance().getRotation(true, cameraRotation);
		
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

		if (keyboard.wasPressed(Keyboard.KEY_SPACE))
		{
			collider.velocity.z = 150;
		}
	}

	private void move(int direction, int elapsedTime)
	{
		float multiplier = elapsedTime * 0.15f;

		float dX = FloatMath.sin(FloatMath.toRadians(cameraRotation.z)) * MOVE_SPEED * multiplier;
		float dY = FloatMath.cos(FloatMath.toRadians(cameraRotation.z)) * MOVE_SPEED * multiplier;

		switch (direction)
		{
			case (FORWARD):
			{
				collider.velocity.x += dX;
				collider.velocity.y += dY;
				break;
			}
			case (BACKWARD):
			{
				collider.velocity.x -= dX;
				collider.velocity.y -= dY;
				break;
			}
			case (RIGHT):
			{
				dX = FloatMath.sin(FloatMath.toRadians(cameraRotation.z + 90)) * MOVE_SPEED * multiplier;
				dY = FloatMath.cos(FloatMath.toRadians(cameraRotation.z + 90)) * MOVE_SPEED * multiplier;

				collider.velocity.x += dX;
				collider.velocity.y += dY;
				break;
			}
			case (LEFT):
			{
				dX = FloatMath.sin(FloatMath.toRadians(cameraRotation.z - 90)) * MOVE_SPEED * multiplier;
				dY = FloatMath.cos(FloatMath.toRadians(cameraRotation.z - 90)) * MOVE_SPEED * multiplier;

				collider.velocity.x += dX;
				collider.velocity.y += dY;
				break;
			}
		}
	}

}

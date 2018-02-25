package zyx.game.components.world.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.world.player.Player;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.MouseData;
import zyx.utils.DeltaTime;

public class FirstPersonBehavior extends Behavior
{

	private Vector3f cameraPosition;
	private Vector3f cameraRotation;
	
	private final Vector3f playerPosition;
	private final Vector3f playerRotation;

	public FirstPersonBehavior(Player player)
	{
		super(BehaviorType.CAMERA_FIRST_PERSON);
				
		playerPosition = player.getPosition();
		playerRotation = player.getRotation();
	}

	@Override
	public void initialize()
	{
		cameraPosition = gameObject.getPosition();
		cameraRotation = gameObject.getRotation();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		float playerX = playerPosition.x;
		float playerY = playerPosition.y;
		float playerZ = playerPosition.z + 30;
		
		cameraPosition.set(-playerX, -playerY, -playerZ);
		
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

}

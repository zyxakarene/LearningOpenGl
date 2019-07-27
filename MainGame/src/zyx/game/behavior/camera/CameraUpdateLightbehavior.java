package zyx.game.behavior.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.World3D;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.KeyboardData;

public class CameraUpdateLightbehavior extends Behavior
{

	public CameraUpdateLightbehavior()
	{
		super(BehaviorType.CAMERA_LIGHT);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (KeyboardData.data.wasPressed(Keyboard.KEY_E))
		{
			Vector3f camDir = gameObject.getDir(false, null);
//			camDir.x *= -1;
//			camDir.z *= -1;
			
			World3D.instance.setSunDir(camDir);
		}
	}
}

package zyx.game.behavior.camera;

import zyx.engine.components.world.World3D;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.InputManager;
import zyx.game.controls.input.scheme.InputScheme;

public class CameraUpdateLightbehavior extends Behavior
{

	private InputScheme inputScheme;

	public CameraUpdateLightbehavior()
	{
		super(BehaviorType.CAMERA_LIGHT);
		inputScheme = InputManager.getInstance().scheme;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (inputScheme.use)
		{
			gameObject.getDir(false, HELPER_DIR);
			HELPER_DIR.y *= -1;
			
			World3D.getInstance().setSunDir(HELPER_DIR);
		}
	}
}

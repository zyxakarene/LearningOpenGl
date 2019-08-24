package zyx.game.behavior.player;

import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.opengl.camera.Camera;

public class CameraFollowBehavior extends Behavior
{

	private Camera camera;

	public CameraFollowBehavior()
	{
		super(BehaviorType.FIRST_PERSON);
	}

	@Override
	public void initialize()
	{
		camera = Camera.getInstance();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		gameObject.getPosition(false, HELPER_POS);
		gameObject.getRotation(true, HELPER_DIR);
		
		camera.setPosition(false, HELPER_POS);
		camera.setRotation(HELPER_DIR);
	}
}

package zyx.game.behavior.player;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;

public class OnlinePositionInterpolator extends Behavior
{

	private static final int MAX_WAIT = GameConstants.PLAYER_POSITION_DELAY;
	private int moveTime = 0;

	private Vector3f moveDir;
	private Vector3f lookAtDir;

	private Vector3f startPos;
	private Vector3f startLook;
	private float moveFract;
	private float lookFract;

	private boolean hasPosChange;
	private boolean hasLookChange;

	public OnlinePositionInterpolator()
	{
		super(BehaviorType.ONLINE_POSITION);

		startLook = new Vector3f();
		startPos = new Vector3f();

		moveDir = new Vector3f();
		lookAtDir = new Vector3f();
	}

	public void setPosition(Vector3f position, Vector3f lookAt)
	{
		gameObject.getPosition(false, startPos);
		gameObject.getDir(false, startLook);
		startLook.x = startPos.x + (startLook.x * 100);
		startLook.y = startPos.y + (startLook.y * 100);
		startLook.z = startPos.z + (startLook.z * 100);

		float moveDistance = FloatMath.distance(position, startPos, true);
		float lookDistance = FloatMath.distance(lookAt, startLook, true);
		moveFract = moveDistance / MAX_WAIT;
		lookFract = lookDistance / MAX_WAIT;
		hasPosChange = moveFract > 0;
		hasLookChange = lookFract > 0;

		if (hasPosChange)
		{
			Vector3f.sub(position, startPos, moveDir);
			moveDir.normalise();
		}

		if (hasLookChange)
		{
			Vector3f.sub(lookAt, startLook, lookAtDir);
			lookAtDir.normalise();
		}
		moveTime = 0;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		moveTime += elapsedTime;
		if (moveTime >= MAX_WAIT)
		{
			moveTime = MAX_WAIT;
		}

		if (hasPosChange)
		{
			float x = startPos.x + (moveDir.x * moveTime * moveFract);
			float y = startPos.y + (moveDir.y * moveTime * moveFract);
			float z = startPos.z + (moveDir.z * moveTime * moveFract);
			gameObject.setPosition(false, x, y, z);
		}

		if (hasLookChange)
		{
			float x = startLook.x + (lookAtDir.x * moveTime * lookFract);
			float y = startLook.y + (lookAtDir.y * moveTime * lookFract);
			float z = startLook.z + (lookAtDir.z * moveTime * lookFract);
			gameObject.lookAt(x, y, z);
		}

		if (moveTime >= MAX_WAIT)
		{
			hasPosChange = false;
			hasLookChange = false;
		}

	}
}

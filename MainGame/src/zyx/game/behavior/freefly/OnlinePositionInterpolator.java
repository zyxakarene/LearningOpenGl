package zyx.game.behavior.freefly;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;

public class OnlinePositionInterpolator extends Behavior
{
	private static final int MAX_WAIT = GameConstants.PLAYER_POSITION_DELAY;
	private int moveTime = 0;
	
	private Vector3f startPos;
	private Vector3f moveDir;
	private float moveFract;
	
	private boolean hasPosChange;
	
	public OnlinePositionInterpolator()
	{
		super(BehaviorType.ONLINE_POSITION);
		
		moveDir = new Vector3f();
		startPos = new Vector3f();
	}

	public void setPosition(Vector3f position, Vector3f dir)
	{
		gameObject.getPosition(false, startPos);
		
		float distance = FloatMath.distance(position, startPos, true);
		moveFract = distance / 50f;
		hasPosChange = moveFract > 0;
		
		if (distance > 0)
		{
			Vector3f.sub(position, startPos, moveDir);
			moveDir.normalise();
		}
		else
		{
			moveDir.set(0, 0, 0);
		}
		
		gameObject.setDir(true, dir);
		
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
		
		if (moveTime >= MAX_WAIT)
		{
			hasPosChange = false;
		}
		
	}
}

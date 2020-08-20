package zyx.game.behavior.player;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.world.characters.CharacterInfo;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.cheats.DebugPoint;

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
	private CharacterInfo info;
	private int timeSinceMoveStart;
	private boolean debug;

	public OnlinePositionInterpolator(CharacterInfo info, boolean debug)
	{
		super(BehaviorType.ONLINE_POSITION);
		this.info = info;
		this.debug = debug;

		startLook = new Vector3f();
		startPos = new Vector3f();

		moveDir = new Vector3f();
		lookAtDir = new Vector3f();
	}

	public void setPosition(Vector3f position, Vector3f lookAt)
	{
		DebugPoint.addToScene(lookAt, 1000);
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
			info.moving = true;
			timeSinceMoveStart = 0;
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
		timeSinceMoveStart += elapsedTime;
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
			info.moving = true;
		}

		if (hasLookChange)
		{
			float x = startLook.x + (lookAtDir.x * moveTime * lookFract);
			float y = startLook.y + (lookAtDir.y * moveTime * lookFract);
			float z = startLook.z + (lookAtDir.z * moveTime * lookFract);
			gameObject.lookAt(x, y, z);
		}
		
		if (timeSinceMoveStart >= MAX_WAIT * 2)
		{
			info.moving = false;
		}
		
		if (moveTime >= MAX_WAIT)
		{
			hasPosChange = false;
			hasLookChange = false;
		}
	}
}

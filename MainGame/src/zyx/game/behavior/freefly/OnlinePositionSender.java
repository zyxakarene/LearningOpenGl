package zyx.game.behavior.freefly;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.cheats.DebugPoint;

public class OnlinePositionSender extends Behavior
{
	private static final int MAX_WAIT = GameConstants.PLAYER_POSITION_DELAY;
	private static final float MIN_DISTANCE = GameConstants.PLAYER_MIN_MOVEMENT;
	private static final float MIN_ROTATION = GameConstants.PLAYER_MIN_ROTATION;
	
	private int waitTime = 0;
	private int id;
	private float lastX;
	private float lastY;
	private float lastZ;
	
	private float lastDirX;
	private float lastDirY;
	private float lastDirZ;
	
	public OnlinePositionSender(int id)
	{
		super(BehaviorType.ONLINE_POSITION);
		this.id = id;
		
		lastX = -999;
		lastY = -999;
		lastZ = -999;
		
		lastDirX = -999;
		lastDirY = -999;
		lastDirZ = -999;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		waitTime -= elapsedTime;
		
		if (waitTime <= 0)
		{
			waitTime = MAX_WAIT;
			
			Vector3f pos = gameObject.getPosition(false, null);
			Vector3f dir = gameObject.getDir(true, null);
			
			float distanceMoved = FloatMath.distance(lastX, lastY, lastZ, pos.x, pos.y, pos.z);
			float distanceRotated = FloatMath.distance(lastDirX, lastDirY, lastDirZ, dir.x, dir.y, dir.z);
			
			if (distanceMoved >= MIN_DISTANCE || distanceRotated >= MIN_ROTATION)
			{
				lastX = pos.x;
				lastY = pos.y;
				lastZ = pos.z;
				
				lastDirX = dir.x;
				lastDirY = dir.y;
				lastDirZ = dir.z;
				
				NetworkChannel.sendRequest(NetworkCommands.PLAYER_UPDATE_POSITION, pos, dir, id);
			}
		}
	}

}

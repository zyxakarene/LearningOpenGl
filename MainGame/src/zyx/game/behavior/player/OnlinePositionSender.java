package zyx.game.behavior.player;

import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.models.GameModels;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;

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
	
	public OnlinePositionSender()
	{
		super(BehaviorType.ONLINE_POSITION);
		this.id = GameModels.player.playerId;
		
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
			
			gameObject.getPosition(false, HELPER_POS);
			gameObject.getDir(false, HELPER_DIR);
			
			//While using directions of the camera!
			HELPER_DIR.x *= -1;
			HELPER_DIR.z *= -1;
			
			float distanceMoved = FloatMath.distance(lastX, lastY, lastZ, HELPER_POS.x, HELPER_POS.y, HELPER_POS.z);
			float distanceRotated = FloatMath.distance(lastDirX, lastDirY, lastDirZ, HELPER_DIR.x, HELPER_DIR.y, HELPER_DIR.z);
			
			if (distanceMoved >= MIN_DISTANCE || distanceRotated >= MIN_ROTATION)
			{
				lastX = HELPER_POS.x;
				lastY = HELPER_POS.y;
				lastZ = HELPER_POS.z;
				
				lastDirX = HELPER_DIR.x;
				lastDirY = HELPER_DIR.y;
				lastDirZ = HELPER_DIR.z;
				
				HELPER_DIR.x = lastX + (HELPER_DIR.x * 100); 
				HELPER_DIR.y = lastY + (HELPER_DIR.y * 100); 
				HELPER_DIR.z = lastZ + (HELPER_DIR.z * 100); 
				
				NetworkChannel.sendRequest(NetworkCommands.CHARACTER_UPDATE_POSITION, HELPER_POS, HELPER_DIR, id);
			}
		}
	}

}

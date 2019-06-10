package zyx.game.behavior.freefly;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;

public class OnlinePositionSender extends Behavior
{
	private int MAX_WAIT = 50;
	private int waitTime = 0;
	private final int id;
	
	public OnlinePositionSender(int id)
	{
		super(BehaviorType.ONLINE_POS_SENDER);
		this.id = id;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		waitTime -= elapsedTime;
		
		if (waitTime <= 0)
		{
			Vector3f pos = gameObject.getPosition(false, null);
			
			waitTime = MAX_WAIT;
			NetworkChannel.sendRequest(NetworkCommands.PLAYER_UPDATE_POSITION, pos, id);
		}
	}

}

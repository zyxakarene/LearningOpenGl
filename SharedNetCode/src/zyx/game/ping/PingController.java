package zyx.game.ping;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PingController
{
	private static final int TIME_BETWEEN_PING = 2000;
	private static final int TIME_FOR_TIMEOUT = TIME_BETWEEN_PING * 6;
	
	private int pingTimer;
	private long lastTime;
	private HashMap<Integer, Long> lastSeenTimestamp;
	private ArrayList<Integer> entityIds;
	
	public PingController()
	{
		lastSeenTimestamp = new HashMap<>();
		entityIds = new ArrayList<>();
	}
	
	public void addEntity(int id)
	{
		System.out.println("Added player " + id + " to ping manager");
		entityIds.add(id);
		lastSeenTimestamp.put(id, lastTime);
	}
	
	public void removeEntity(Integer id)
	{
		System.out.println("removed player " + id + " to ping manager");
		entityIds.remove(id);
		lastSeenTimestamp.remove(id);
	}
	
	public void update(long timestamp, int elapsedTime)
	{
		lastTime = timestamp;
		pingTimer += elapsedTime;
		boolean shouldPing = pingTimer >= TIME_BETWEEN_PING;
		
		int len = entityIds.size() - 1;
		for (int i = len; i >= 0; i--)
		{
			int id = entityIds.get(i);
			long lastSeen = lastSeenTimestamp.get(id);
			
			long diff = lastTime - lastSeen;
			
			if (diff >= TIME_FOR_TIMEOUT)
			{
				System.out.println("player " + id + " has been AFK for too long!");
				removeEntity(id);
				
				onPingTimeout(id);
			}
			else if (shouldPing)
			{
				onSendPingTo(id);
			}
		}
		
		if (shouldPing)
		{
			pingTimer = 0;
		}
	}

	public void onPing(int id)
	{
		lastSeenTimestamp.put(id, lastTime);
	}
	
	protected abstract void onPingTimeout(int id);
	protected abstract void onSendPingTo(int id);
}

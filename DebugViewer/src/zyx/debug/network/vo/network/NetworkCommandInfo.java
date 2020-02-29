package zyx.debug.network.vo.network;

import java.util.ArrayList;

public class NetworkCommandInfo
{
	public String command;
	public ArrayList<NetworkData> data;
	public ArrayList<Long> timestamps;
	public long lastTimestamp;
	
	public int count;

	public NetworkCommandInfo(String name)
	{
		this.command = name;
		this.data = new ArrayList<>();
		this.timestamps = new ArrayList<>();
		this.count = 0;
		this.lastTimestamp = 0;
	}
	
	public void addData(NetworkData obj, long time)
	{
		data.add(obj);
		timestamps.add(time);
		
		if (data.size() > 10)
		{
			data.remove(0);
			timestamps.remove(0);
		}
		
		count = data.size();
		lastTimestamp = time;
	}

	@Override
	public String toString()
	{
		return lastTimestamp + " - " + command + "(" + count + ")";
	}
	
	
}

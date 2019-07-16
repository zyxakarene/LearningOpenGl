package zyx.debug.views.network;

import java.util.ArrayList;
import zyx.net.data.ReadableDataObject;
import zyx.utils.DeltaTime;

public class NetworkInfo
{
	String command;
	ArrayList<ReadableDataObject> data;
	ArrayList<Long> timestamps;
	long lastTimestamp;
	
	int count;

	public NetworkInfo(String name)
	{
		this.command = name;
		this.data = new ArrayList<>();
		this.timestamps = new ArrayList<>();
		this.count = 0;
		this.lastTimestamp = 0;
	}
	
	public void addData(ReadableDataObject obj)
	{
		data.add(obj);
		timestamps.add(DeltaTime.getTimestamp());
		
		if (data.size() > 10)
		{
			data.remove(0);
			timestamps.remove(0);
		}
		
		count = data.size();
		lastTimestamp = DeltaTime.getTimestamp();
	}

	@Override
	public String toString()
	{
		return lastTimestamp + " - " + command + "(" + count + ")";
	}
	
	
}

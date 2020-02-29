package zyx.debug.network.vo.network;

import java.util.HashMap;
import java.util.Set;

public class NetworkData
{
	public HashMap<String, NetworkDataType> typeMap;
	public HashMap<String, Object> dataMap;

	public NetworkData()
	{
		typeMap = new HashMap<>();
		dataMap = new HashMap<>();
	}
	
	public void addData(String key, Object data, NetworkDataType type)
	{
		typeMap.put(key, type);
		dataMap.put(key, data);
	}

	public String[] getAllKeys()
	{
		Set<String> keySet = dataMap.keySet();
		String[] keys = new String[keySet.size()];
		
		return keySet.toArray(keys);
	}
}

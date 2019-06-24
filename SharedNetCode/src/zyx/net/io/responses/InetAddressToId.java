package zyx.net.io.responses;

import java.net.InetAddress;
import java.util.HashMap;

class InetAddressToId
{
	private static final String KEY_FORMAT = "%s:%s";
	private static final String EMPTY_HOST = "EMPTY_HOST";
	private static final HashMap<String, Integer> KEY_MAP = new HashMap<>();
	
	private static int idCounter = 0;
	
	static int getIdFromAddress(InetAddress address, int port)
	{
		String key;
		if (address != null)
		{
			key = String.format(KEY_FORMAT, address.getHostAddress(), port);
		}
		else
		{
			key = String.format(KEY_FORMAT, EMPTY_HOST, port);
		}
		
		Integer id = KEY_MAP.get(key);
		if (id != null)
		{
			return id;
		}
		else
		{
			int newId = idCounter++;
			KEY_MAP.put(key, newId);
			
			return newId;
		}
	}
}

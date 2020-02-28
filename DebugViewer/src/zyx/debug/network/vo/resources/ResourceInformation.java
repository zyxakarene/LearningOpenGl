package zyx.debug.network.vo.resources;

import java.util.ArrayList;

public class ResourceInformation
{
	private static final ArrayList<String> TO_RELOAD = new ArrayList<>();
	
	public static boolean hasResourceChanges;
	public static ArrayList<ResourceInfo> resourceInfo;

	public static void resourceRefreshed(String path)
	{
		synchronized(TO_RELOAD)
		{
			TO_RELOAD.add(path);
		}
	}

	public static void getReloadedResources(ArrayList<String> out)
	{
		synchronized(TO_RELOAD)
		{
			out.addAll(TO_RELOAD);
			TO_RELOAD.clear();
		}
	}
}

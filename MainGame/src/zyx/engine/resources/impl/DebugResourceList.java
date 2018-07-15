package zyx.engine.resources.impl;

import java.util.ArrayList;

public class DebugResourceList
{
	private static final ArrayList<Resource> RESOURCES = new ArrayList<>();
	
	static void addResource(Resource resource)
	{
		synchronized(RESOURCES)
		{
			if (RESOURCES.contains(resource) == false)
			{
				RESOURCES.add(resource);
			}
		}
	}
	
	static void removeResource(Resource resource)
	{
		synchronized(RESOURCES)
		{
			RESOURCES.remove(resource);
		}
	}
	
	public static boolean getActiveResources(ArrayList<Resource> lastList)
	{
		synchronized(RESOURCES)
		{
			boolean changes = false;
			for (Resource resource : lastList)
			{
				if (RESOURCES.contains(resource) == false)
				{
					changes = true;
					break;
				}
			}
			
			for (Resource resource : RESOURCES)
			{
				if (lastList.contains(resource) == false)
				{
					changes = true;
					break;
				}
			}
			
			if (changes)
			{
				lastList.clear();
				lastList.addAll(RESOURCES);
			}
			
			return changes;
		}
	}
}

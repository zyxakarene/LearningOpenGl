package zyx.debug.link;

import java.util.ArrayList;
import zyx.engine.resources.impl.Resource;

public class DebugResourceLink
{
	private final ArrayList<Resource> RESOURCES = new ArrayList<>();

	DebugResourceLink()
	{
	}

	public void addResource(Resource resource)
	{
		synchronized(RESOURCES)
		{
			if (RESOURCES.contains(resource) == false)
			{
				RESOURCES.add(resource);
			}
		}
	}
	
	public void removeResource(Resource resource)
	{
		synchronized(RESOURCES)
		{
			RESOURCES.remove(resource);
		}
	}
	
	public boolean getActiveResources(ArrayList<Resource> lastList)
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

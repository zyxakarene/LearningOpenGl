package zyx.game.controls.resourceloader;

import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.utils.interfaces.IDisposeable;

class ResourceRunner implements Runnable, IDisposeable
{

	private boolean enabled = true;

	@Override
	public void run()
	{
		while (enabled)
		{
			ResourceRequest request = ResourceExchange.getRequest();
			if (request != null)
			{
				FileLoader loader = new FileLoader(request);
				loader.loadFile();
								
				ResourceExchange.addCompleted(request);
			}
			else
			{
				ResourceExchange.sleep();
			}
			
		}
	}

	@Override
	public void dispose()
	{
		enabled = false;
	}

}

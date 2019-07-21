package zyx.game.controls.resourceloader;

import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.synchronizer.BaseAsyncSynchronizer;

public class ResourceLoader extends BaseAsyncSynchronizer<ResourceRequest, ResourceRequest>
{

	private static final ResourceLoader INSTANCE = new ResourceLoader();

	public static ResourceLoader getInstance()
	{
		return INSTANCE;
	}

	private ResourceExchange exchange;
	
	public ResourceLoader()
	{
		exchange = new ResourceExchange();
		setExchange(exchange);
	}	

	@Override
	public void addThreads(int count)
	{
		for (int i = 0; i < count; i++)
		{
			ResourceRunner runner = new ResourceRunner();
			addRunner(runner);
		}
	}
}

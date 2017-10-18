package dev.resourceloader;

import dev.resourceloader.requests.ResourceRequest;
import java.util.ArrayList;
import zyx.utils.interfaces.IDisposeable;

public class ResourceLoader implements IDisposeable
{

	private static final ResourceLoader INSTANCE = new ResourceLoader();

	public static ResourceLoader getInstance()
	{
		return INSTANCE;
	}

	private final ArrayList<ResourceRunner> runners = new ArrayList<>();

	private ResourceLoader()
	{
	}

	public void initialize(int threadCount)
	{
		Thread loader;
		ResourceRunner runner;
		for (int i = 0; i < threadCount; i++)
		{
			runner = new ResourceRunner();
			loader = new Thread(runner);
			loader.setDaemon(true);

			runners.add(runner);

			loader.start();
		}
	}

	public void handleResourceReplies()
	{
		ResourceExchange.sendReplies();
	}

	public void addRequest(ResourceRequest request)
	{
		ResourceExchange.addLoad(request);
	}

	@Override
	public void dispose()
	{
		for (ResourceRunner runner : runners)
		{
			runner.dispose();
		}

		runners.clear();
	}
}

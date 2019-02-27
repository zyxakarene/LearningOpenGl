package zyx.game.controls.resourceloader;

import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.synchronizer.BaseRunner;

class ResourceRunner extends BaseRunner<ResourceRequest, ResourceRequest>
{
	@Override
	protected ResourceRequest handleEntry(ResourceRequest entry)
	{
		FileLoader loader = new FileLoader(entry);
		loader.loadFile();
		
		return entry;
	}
	
	@Override
	protected String getName()
	{
		return "ResourceLoaderRunner";
	}

}

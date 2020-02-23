package zyx.game.controls.resourceloader;

import java.util.HashMap;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.synchronizer.BaseExchange;

class ResourceExchange extends BaseExchange<ResourceRequest, ResourceRequest>
{

	private HashMap<String, ResourceRequest> requestMap;

	public ResourceExchange()
	{
		requestMap = new HashMap<>();
	}

	@Override
	protected void onReplyCompleted(ResourceRequest request)
	{
		requestMap.remove(request.path);

		if (request.requestCompleted)
		{
			request.complete(request.getData());
		}
		else
		{
			request.fail();
		}

		request.dispose();
	}

	@Override
	protected void onRemoveEntry(ResourceRequest request)
	{
		requestMap.remove(request.path);
	}

	@Override
	protected void onRemoveReply(ResourceRequest request)
	{
		requestMap.remove(request.path);
	}

	@Override
	protected boolean shouldAddEntry(ResourceRequest request)
	{
		if (requestMap.containsKey(request.path))
		{
			ResourceRequest otherRequest = requestMap.get(request.path);
			otherRequest.mergeFrom(request);

			request.dispose();
			return false;
		}

		requestMap.put(request.path, request);

		return true;
	}
}

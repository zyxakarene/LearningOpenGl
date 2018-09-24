package zyx.game.components.screen.json;

import org.json.simple.JSONObject;
import zyx.engine.components.screen.DisplayObjectContainer;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.JsonResource;
import zyx.utils.interfaces.IUpdateable;

public class JsonSprite extends DisplayObjectContainer implements IResourceReady<JsonResource>, IUpdateable
{
	private JsonResource resource;
	
	public JsonSprite()
	{
		String res = getResource();
		if (res != null)
		{
			resource = (JsonResource) ResourceManager.getInstance().getResource(res);
			resource.registerAndLoad(this);
		}
	}
	
	public String getResource()
	{
		return null;
	}

	@Override
	public void onResourceReady(JsonResource resource)
	{
		JSONObject content = resource.getContent();
		JsonSpriteParser.getInstance().createSpriteFrom(this, content);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}
	
	@Override
	public final void dispose()
	{
		super.dispose();
		
		onDispose();
	}

	protected void onDispose()
	{
	}
}

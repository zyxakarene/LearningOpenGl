package zyx.game.components.screen.json;

import org.json.simple.JSONObject;
import zyx.engine.components.screen.DisplayObjectContainer;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.JsonResource;

public class JsonSprite extends DisplayObjectContainer implements IResourceReady<JsonResource>
{
	private JsonResource resource;
	
	public String name;
	
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
	public final void dispose()
	{
		super.dispose();
		
		onDispose();
	}

	protected void onDispose()
	{
	}
}

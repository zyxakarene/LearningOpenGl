package zyx.engine.resources.impl;

import java.nio.charset.Charset;
import java.util.logging.Level;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zyx.utils.GameConstants;

public class JsonResource extends ExternalResource
{

	private JSONObject json;

	public JsonResource(String path)
	{
		super(path);
	}

	@Override
	public JSONObject getContent()
	{
		return json;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream jsonData)
	{
		parseJsonData(jsonData);
		onContentLoaded(json);
	}

	@Override
	protected void onResourceReloaded(ResourceDataInputStream jsonData)
	{
		parseJsonData(jsonData);
		resourceReloaded();
	}

	private void parseJsonData(ResourceDataInputStream jsonData)
	{
		try
		{
			String text = new String(jsonData.getData(), Charset.defaultCharset());
			json = (JSONObject) new JSONParser().parse(text);
		}
		catch (ParseException ex)
		{
			String msg = String.format("Could not parse JSON file from resource: %s at position %s", path, ex.getPosition());
			throw new RuntimeException(msg);
		}
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();

		if (json != null)
		{
			json.clear();
			json = null;
		}
	}
	
	@Override
	public String getResourceIcon()
	{
		return "json.png";
	}
}

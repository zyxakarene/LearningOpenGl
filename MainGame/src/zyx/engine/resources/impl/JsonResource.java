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
		try
		{
			String text = new String(jsonData.getData(), Charset.defaultCharset());

			json = (JSONObject) new JSONParser().parse(text);

			onContentLoaded(json);
		}
		catch (ParseException ex)
		{
			Object[] params = new Object[]
			{
				path, ex.getPosition()
			};
			GameConstants.LOGGER.log(Level.WARNING, "Could not parse JSON file from resource: {0} - {1}", params);
			
			json = new JSONObject();
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
}

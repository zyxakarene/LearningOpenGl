package zyx.engine.resources.impl;

import java.nio.charset.Charset;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zyx.utils.PrintBuilder;
import zyx.utils.cheats.Print;

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
		PrintBuilder builder = new PrintBuilder();

		try
		{
			builder.append("==== Parsing json data from byte count:", jsonData.getLength(), "====");
			builder.append("↳ Id", path);
			
			String text = new String(jsonData.getData(), Charset.defaultCharset());
			json = (JSONObject) new JSONParser().parse(text);
			
			builder.append("========");
			
			Print.out(builder);
		}
		catch (ParseException ex)
		{
			builder.append("Error at position ", ex.getPosition());
			builder.append(String.format("==== [ERROR] Failed to parse json! ===="));

			Print.err(builder);
			
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
	
	@Override
	public String getResourceIcon()
	{
		return "json.png";
	}
}

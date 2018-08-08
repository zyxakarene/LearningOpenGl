package zyx.engine.resources.impl;

import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zyx.utils.geometry.Rectangle;

public class SpriteSheetJsonResource extends JsonResource
{

	private static final String FRAMES = "frames";
	private static final String FILENAME = "filename";
	private static final String FRAME = "frame";

	private static final String X = "x";
	private static final String Y = "y";
	private static final String W = "w";
	private static final String H = "h";
	private static final String DOT_PNG = ".png";
	private static final String EMPTY_STRING = "";

	private HashMap<String, Rectangle> map;

	public SpriteSheetJsonResource(String path)
	{
		super(path);

		map = new HashMap<>();
	}

	Rectangle getById(String id)
	{
		return map.get(id);
	}

	@Override
	protected void onContentLoaded(Object content)
	{
		JSONObject jsonFile = (JSONObject) content;
		JSONArray frames = (JSONArray) jsonFile.get(FRAMES);

		String filename;
		JSONObject size;
		JSONObject item;
		int len = frames.size();
		for (int i = 0; i < len; i++)
		{
			item = (JSONObject) frames.get(i);

			filename = (String) item.get(FILENAME);
			size = (JSONObject) item.get(FRAME);

			addFromItem(filename, size);
		}

		super.onContentLoaded(content);
	}

	private void addFromItem(String filename, JSONObject item)
	{
		filename = filename.replace(DOT_PNG, EMPTY_STRING);

		Long x = (Long) item.get(X);
		Long y = (Long) item.get(Y);
		Long w = (Long) item.get(W);
		Long h = (Long) item.get(H);

		Rectangle rect = new Rectangle(x, y, w, h);

		map.put(filename, rect);
	}

}

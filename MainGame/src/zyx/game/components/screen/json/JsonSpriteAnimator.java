package zyx.game.components.screen.json;

import java.util.ArrayList;
import zyx.utils.interfaces.IUpdateable;

public class JsonSpriteAnimator implements IUpdateable
{
	private static final JsonSpriteAnimator INSTANCE = new JsonSpriteAnimator();
	
	private ArrayList<JsonSprite> jsonSprites;
	
	public static JsonSpriteAnimator getInstance()
	{
		return INSTANCE;
	}
	
	private JsonSpriteAnimator()
	{
		jsonSprites = new ArrayList<>();
	}
	
	void addJsonSprite(JsonSprite sprite)
	{
		jsonSprites.add(sprite);
	}
	
	void removeJsonSprite(JsonSprite sprite)
	{
		jsonSprites.remove(sprite);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (JsonSprite sprite : jsonSprites)
		{
			sprite.update(timestamp, elapsedTime);
		}
	}
}

package zyx.game.components.screen.json;

import java.util.ArrayList;
import zyx.engine.utils.ScreenSize;
import zyx.utils.interfaces.IUpdateable;
import zyx.utils.math.Vector2Int;

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
		ScreenSize.addListener(this::onSizeChanged);
	}
	
	private void onSizeChanged(Vector2Int size)
	{
		JsonSprite[] array = jsonSprites.toArray(new JsonSprite[jsonSprites.size()]);
		for (JsonSprite sprite : array)
		{
			if (sprite.disposed == false)
			{
				sprite.onResourceReloaded(null);
			}
		}
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

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
	
	void addAnimatedMesh(JsonSprite mesh)
	{
		jsonSprites.add(mesh);
	}
	
	void removeAnimatedMesh(JsonSprite mesh)
	{
		jsonSprites.remove(mesh);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (JsonSprite mesh : jsonSprites)
		{
			mesh.update(timestamp, elapsedTime);
		}
	}
}

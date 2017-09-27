package zyx.opengl.textures;

import java.util.HashMap;
import org.newdawn.slick.opengl.Texture;

public class TextureManager
{
	private static HashMap<String, GameTexture> MAP = new HashMap<>();
	
	public static GameTexture getTexture(String name)
	{
		if (MAP.containsKey(name) == false)
		{
			Texture tex = TextureUtils.createTexture(name, "png");
			MAP.put(name, new GameTexture(name, "png"));
		}
		
		return MAP.get(name);
	}
}

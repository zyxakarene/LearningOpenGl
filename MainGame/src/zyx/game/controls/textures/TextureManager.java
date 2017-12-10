package zyx.game.controls.textures;

import java.util.HashMap;
import zyx.opengl.textures.GameTexture;
import zyx.utils.GameConstants;

public class TextureManager
{

	private static HashMap<String, GameTexture> MAP = new HashMap<>();

	public static GameTexture getTexture(String name)
	{
		String path = String.format("assets/textures/%s.%s", name, GameConstants.TEXTURE_FORMAT);
		return getTextureInner(path);
	}

	public static GameTexture getFontTexture(String name)
	{
		String path = String.format("assets/fonts/%s.%s", name, GameConstants.TEXTURE_FORMAT);
		return getTextureInner(path);
	}

	private static GameTexture getTextureInner(String path)
	{
		if (MAP.containsKey(path) == false)
		{
			MAP.put(path, new GameTexture(path));
		}

		return MAP.get(path);
	}
}

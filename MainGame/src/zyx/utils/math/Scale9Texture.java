package zyx.utils.math;

import java.util.HashMap;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.SubTexture;
import zyx.utils.geometry.Rectangle;

public class Scale9Texture
{
	public static final String TOP_LEFT = "tl";
	public static final String TOP_MIDDLE = "tm";
	public static final String TOP_RIGHT = "tr";
	
	public static final String MIDDLE_LEFT = "ml";
	public static final String MIDDLE_MIDDLE = "mm";
	public static final String MIDDLE_RIGHT = "mr";
	
	public static final String LOWER_LEFT = "ll";
	public static final String LOWER_MIDDLE = "lm";
	public static final String LOWER_RIGHT = "lr";
	
	private static final HashMap<String, HashMap<String, AbstractTexture>> CACHE = new HashMap<>();
	private static final HashMap<String, AbstractTexture> OUT = new HashMap<>();

	public static HashMap<String, AbstractTexture> ToScale9TextureMap(Rectangle grid, AbstractTexture baseTexture)
	{
		String baseName = baseTexture.getName();
		String key = baseName + "-" + grid.x + "," + grid.y + ":" + grid.width + "," + grid.height;
		
		if (CACHE.containsKey(key))
		{
			HashMap<String, AbstractTexture> cacheEntry = CACHE.get(key);
			OUT.clear();
			
			OUT.putAll(cacheEntry);
		}
		else
		{
			HashMap<String, AbstractTexture> createdEntry = createEntry(grid, baseTexture);
			
			CACHE.put(key, createdEntry);
			
			OUT.clear();
			OUT.putAll(createdEntry);
		}
		
		return OUT;
	}

	private static HashMap<String, AbstractTexture> createEntry(Rectangle grid, AbstractTexture texture)
	{
		String name = texture.getName();
		Rectangle rect = new Rectangle();
		HashMap<String, AbstractTexture> entry = new HashMap<>();
		
		float texelWidthPerPixel = (texture.u - texture.x) / texture.getWidth();
		float texelHeightPerPixel = (texture.v - texture.y) / texture.getHeight();
		
		float leftX = texture.x + (grid.x * texelWidthPerPixel);
		float rightX = leftX + (grid.width * texelWidthPerPixel);
		
		float topY = texture.y + (grid.y * texelHeightPerPixel);
		float btmY = topY + (grid.height * texelHeightPerPixel);
				
		//Top left
		rect.x = texture.x;
		rect.y = texture.y;
		rect.width = leftX;
		rect.height = topY;
		entry.put(TOP_LEFT, new SubTexture(texture, rect, name + TOP_LEFT));
		
		//Top mid
		rect.x = leftX;
		rect.y = texture.y;
		rect.width = rightX;
		rect.height = topY;
		entry.put(TOP_MIDDLE, new SubTexture(texture, rect, name + TOP_MIDDLE));
		
		//Top right
		rect.x = rightX;
		rect.y = texture.y;
		rect.width = texture.u;
		rect.height = topY;
		entry.put(TOP_RIGHT, new SubTexture(texture, rect, name + TOP_RIGHT));
				
		//Mid left
		rect.x = texture.x;
		rect.y = topY;
		rect.width = leftX;
		rect.height = btmY;
		entry.put(MIDDLE_LEFT, new SubTexture(texture, rect, name + MIDDLE_LEFT));
		
		//Mid mid
		rect.x = leftX;
		rect.y = topY;
		rect.width = rightX;
		rect.height = btmY;
		entry.put(MIDDLE_MIDDLE, new SubTexture(texture, rect, name + MIDDLE_MIDDLE));
		
		//Mid right
		rect.x = rightX;
		rect.y = topY;
		rect.width = texture.u;
		rect.height = btmY;
		entry.put(MIDDLE_RIGHT, new SubTexture(texture, rect, name + MIDDLE_RIGHT));
				
		//Lower left
		rect.x = texture.x;
		rect.y = btmY;
		rect.width = leftX;
		rect.height = texture.v;
		entry.put(LOWER_LEFT, new SubTexture(texture, rect, name + LOWER_LEFT));
		
		//Lower mid
		rect.x = leftX;
		rect.y = btmY;
		rect.width = rightX;
		rect.height = texture.v;
		entry.put(LOWER_MIDDLE, new SubTexture(texture, rect, name + LOWER_MIDDLE));
		
		//Lower right
		rect.x = rightX;
		rect.y = btmY;
		rect.width = texture.u;
		rect.height = texture.v;
		entry.put(LOWER_RIGHT, new SubTexture(texture, rect, name + LOWER_RIGHT));

		return entry;
	}

}

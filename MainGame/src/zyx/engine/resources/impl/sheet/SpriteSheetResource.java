package zyx.engine.resources.impl.sheet;

import zyx.engine.resources.impl.textures.TextureResource;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.MissingTexture;
import zyx.opengl.textures.SubTexture;
import zyx.utils.cheats.Print;
import zyx.utils.geometry.Rectangle;

public class SpriteSheetResource extends TextureResource implements IResourceReady<Resource>
{
	private SpriteSheetJsonResource jsonResource;
	private SpriteSheetTextureResource textureResource;
	private int loadCompleteCounter;
	
	private SubTexture subTexture;
	
	public SpriteSheetResource(String path)
	{
		super(path);
	}

	@Override
	public void onBeginLoad()
	{
		resourceLoaded(null);
	}
	
	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		textureResource = (SpriteSheetTextureResource) ResourceManager.getInstance().getResource("sprite_sheet_png");
		textureResource.registerAndLoad(this);
		
		jsonResource = (SpriteSheetJsonResource) ResourceManager.getInstance().getResource("sprite_sheet_json");
		jsonResource.registerAndLoad(this);
	}

	@Override
	public void onResourceReady(Resource resource)
	{
		loadCompleteCounter++;
		
		if (loadCompleteCounter >= 2)
		{
			Rectangle rect = jsonResource.getById(path);
			AbstractTexture textureSheet = textureResource.getContent();
			
			AbstractTexture tex = null;
			if (rect == null)
			{
				tex = MissingTexture.getAsSlot(textureSheet.slot);
				Print.err("Could not load SpriteSheet icon:", path);
			}
			else
			{
				float width = textureSheet.getWidth();
				float height = textureSheet.getHeight();

				float ratioW = 1/width;
				float ratioH = 1/height;

				float x = rect.x * ratioW;
				float y = rect.y * ratioH;
				float w = x + (rect.width * ratioW);
				float h = y + (rect.height * ratioH);

				Rectangle uvs = new Rectangle(x, y, w, h);

				subTexture = new SubTexture(textureSheet, uvs, path);
				tex = subTexture;
				
				textureResource.addSubTexture(subTexture);
			}
			
			resourceCreated(tex);
		}
	}

	@Override
	public boolean isLoaded()
	{
		return subTexture != null;
	}
	
	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		if (textureResource != null)
		{
			textureResource.removeSubTexture(subTexture);
			
			textureResource.unregister(this);
			textureResource = null;
		}
		
		if (jsonResource != null)
		{
			jsonResource.unregister(this);
			jsonResource = null;
		}
	}
	
	@Override
	public String getDebugIcon()
	{
		return "spritesheet.png";
	}
	
}

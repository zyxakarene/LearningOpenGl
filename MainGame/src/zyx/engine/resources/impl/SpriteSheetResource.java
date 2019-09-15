package zyx.engine.resources.impl;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.GameTexture;
import zyx.opengl.textures.SubTexture;
import zyx.utils.geometry.Rectangle;

public class SpriteSheetResource extends TextureResource implements IResourceReady<Resource>
{
	private SpriteSheetJsonResource jsonResource;
	private TextureResource textureResource;
	private int loadCompleteCounter;
	
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
		textureResource = (TextureResource) ResourceManager.getInstance().getResource("sprite_sheet_png");
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
			GameTexture textureSheet = textureResource.getContent();
			
			float width = textureSheet.getWidth();
			float height = textureSheet.getHeight();
			
			float ratioW = 1/width;
			float ratioH = 1/height;
			
			float x = rect.x * ratioW;
			float y = rect.y * ratioH;
			float w = x + (rect.width * ratioW);
			float h = y + (rect.height * ratioH);
			
			Rectangle uvs = new Rectangle(x, y, w, h);
			
			SubTexture subTexture = new SubTexture(textureSheet, uvs, path);
			resourceCreated(subTexture);
		}
	}

	@Override
	void onDispose()
	{
		super.onDispose();
		
		if (textureResource != null)
		{
			textureResource.unregister(this);
			textureResource = null;
		}
		
		if (jsonResource != null)
		{
			jsonResource.unregister(this);
			jsonResource = null;
		}
	}

	
}

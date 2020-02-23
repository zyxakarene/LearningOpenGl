package zyx.engine.resources.impl.sheet;

import java.util.ArrayList;
import zyx.engine.resources.impl.textures.TextureResource;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.SubTexture;

public class SpriteSheetTextureResource extends TextureResource
{
	private ArrayList<SubTexture> subTextures;
	
	public SpriteSheetTextureResource(String path)
	{
		super(path);
		
		subTextures = new ArrayList<>();
	}

	void addSubTexture(SubTexture texture)
	{
		subTextures.add(texture);
	}

	void removeSubTexture(SubTexture texture)
	{
		subTextures.remove(texture);
	}
	
	@Override
	protected void onResourceReloaded(ResourceDataInputStream data)
	{
		super.onResourceReloaded(data);
		
		AbstractTexture texture = getContent();
		for (SubTexture subTexture : subTextures)
		{
			subTexture.refresh(texture);
		}
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		if (subTextures.isEmpty() == false)
		{
			String msg = String.format("%s still had subTextures before being disposed. Count: %s", this, subTextures.size());
			throw new RuntimeException(msg);
		}
	}
}

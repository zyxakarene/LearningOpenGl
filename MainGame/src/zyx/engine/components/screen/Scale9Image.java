package zyx.engine.components.screen;

import java.util.HashMap;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.resources.impl.TextureResource;
import zyx.opengl.textures.GameTexture;
import zyx.utils.geometry.Rectangle;
import zyx.utils.math.Scale9Texture;

public class Scale9Image extends DisplayObjectContainer implements IResourceReady<TextureResource>
{

	private Image tl;
	private Image tm;
	private Image tr;
	private Image ml;
	private Image mm;
	private Image mr;
	private Image ll;
	private Image lm;
	private Image lr;

	private Resource textureResource;

	public Scale9Image()
	{
		tl = new Image();
		tm = new Image();
		tr = new Image();
		ml = new Image();
		mm = new Image();
		mr = new Image();
		ll = new Image();
		lm = new Image();
		lr = new Image();
	}

	public void load(String resource)
	{
		textureResource = ResourceManager.getInstance().getResource(resource);
		textureResource.registerAndLoad(this);
	}

	@Override
	public void onResourceReady(TextureResource resource)
	{
		Rectangle grid = new Rectangle(25, 25, 14, 14);
		
		GameTexture texture = resource.getContent();
		
		HashMap<String, GameTexture> textureMap = Scale9Texture.ToScale9TextureMap(grid, texture);
		
		tl.setTexture(textureMap.get(Scale9Texture.TOP_LEFT));
		tm.setTexture(textureMap.get(Scale9Texture.TOP_MIDDLE));
		tr.setTexture(textureMap.get(Scale9Texture.TOP_RIGHT));
		
		ml.setTexture(textureMap.get(Scale9Texture.MIDDLE_LEFT));
		mm.setTexture(textureMap.get(Scale9Texture.MIDDLE_MIDDLE));
		mr.setTexture(textureMap.get(Scale9Texture.MIDDLE_RIGHT));
		
		ll.setTexture(textureMap.get(Scale9Texture.LOWER_LEFT));
		lm.setTexture(textureMap.get(Scale9Texture.LOWER_MIDDLE));
		lr.setTexture(textureMap.get(Scale9Texture.LOWER_RIGHT));
		
		addChild(tl);
		addChild(tm);
		addChild(tr);
		
		addChild(ml);
		addChild(mm);
		addChild(mr);
		
		addChild(ll);
		addChild(lm);
		addChild(lr);
		
		tm.setWidth(100);
		mm.setWidth(100);
		lm.setWidth(100);
		
		ml.setHeight(100);
		mm.setHeight(100);
		mr.setHeight(100);
		
		float leftX = 0;
		float middleX = tl.getWidth();
		float rightX = middleX + tm.getWidth();
		
		float topY = 0;
		float middleY = tl.getHeight();
		float lowerY = middleY + ml.getHeight();
		
		tl.setPosition(true, leftX, topY);
		tm.setPosition(true, middleX, topY);
		tr.setPosition(true, rightX, topY);
		
		ml.setPosition(true, leftX, middleY);
		mm.setPosition(true, middleX, middleY);
		mr.setPosition(true, rightX, middleY);
		
		ll.setPosition(true, leftX, lowerY);
		lm.setPosition(true, middleX, lowerY);
		lr.setPosition(true, rightX, lowerY);
	}
}

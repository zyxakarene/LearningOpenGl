package zyx.engine.components.screen.base.generic;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.utils.geometry.Rectangle;

public class Panel extends DisplayObjectContainer
{
	private static final Rectangle CLIP_HELPER = new Rectangle();
	private Scale9Image bgImage;
	
	public Panel(int width, int height)
	{
		bgImage = new Scale9Image();
		bgImage.load("container");
		
		bgImage.setSize(width, height);
		
		addChild(bgImage);
	}
	
//	@Override
//	protected void onDraw()
//	{
//		shader.getClipRect(CLIP_HELPER);
//		shader.setClipRect(clipRect.x, clipRect.width, clipRect.y, clipRect.height);
//		
//		super.onDraw();
//		
//		shader.setClipRect(CLIP_HELPER.x, CLIP_HELPER.width, CLIP_HELPER.y, CLIP_HELPER.height);
//	}
//
//	@Override
//	protected void onWorldMatrixUpdated()
//	{
//		getPosition(false, HELPER_VEC2);
//		clipRect.x = HELPER_VEC2.x;
//		clipRect.y = HELPER_VEC2.y;
//	}
}

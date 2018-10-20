package zyx.engine.components.screen.list;

import java.util.ArrayList;
import java.util.Collection;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.Quad;
import zyx.utils.geometry.Rectangle;

public class ItemList<T extends ItemRenderer> extends DisplayObjectContainer
{
	private static final Rectangle CLIP_HELPER = new Rectangle();

	private Class<T> itemRenderer;

	private ArrayList<T> itemRenderers;
	private ArrayList<Object> rendererData;

	private Quad background;

	private Rectangle clipRect;
	
	public ItemList()
	{
		itemRenderers = new ArrayList<>();
		rendererData = new ArrayList<>();

		background = new Quad(10, 10, 0xFFFFFF);
		background.setAlpha(0.5f);

		clipRect = new Rectangle();
		
		addChild(background);
	}

	public void setItemRenderer(Class<T> clazz)
	{
		itemRenderer = clazz;
	}

	public void setData(Collection<Object> data)
	{
		rendererData.clear();
		rendererData.addAll(data);

		onDataChanged();
	}

	@Override
	protected void onDraw()
	{
		shader.getClipRect(CLIP_HELPER);
		shader.setClipRect(clipRect.x, clipRect.width, clipRect.y, clipRect.height);
		
		super.onDraw();
		
		shader.setClipRect(CLIP_HELPER.x, CLIP_HELPER.width, CLIP_HELPER.y, CLIP_HELPER.height);
	}

	@Override
	protected void onWorldMatrixUpdated()
	{
		getPosition(false, HELPER_VEC2);
		clipRect.x = HELPER_VEC2.x;
		clipRect.y = HELPER_VEC2.y;
		clipRect.width = clipRect.x + background.getWidth();
		clipRect.height = clipRect.y + background.getHeight();
	}
	
	private void onDataChanged()
	{
		float xPos = 0;
		
		for (Object data : rendererData)
		{
			T renderer = ListRendererFactory.createFrom(itemRenderer);
			itemRenderers.add(renderer);
			
			renderer.setData(data);
			
			addChild(renderer);
			
			renderer.setX(xPos);
			xPos += renderer.getWidth();
		}
	}

	@Override
	public void setWidth(float value)
	{
		background.setWidth(value);
	}

	@Override
	public void setHeight(float value)
	{
		background.setHeight(value);
	}
}

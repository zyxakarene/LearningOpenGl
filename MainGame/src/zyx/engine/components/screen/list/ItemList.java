package zyx.engine.components.screen.list;

import java.util.ArrayList;
import java.util.Collection;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.touch.ITouched;
import zyx.engine.components.screen.base.Quad;
import zyx.engine.touch.TouchData;
import zyx.engine.touch.TouchState;
import zyx.utils.geometry.Rectangle;

public class ItemList<T extends ItemRenderer> extends DisplayObjectContainer implements ITouched
{
	private static final Rectangle CLIP_HELPER = new Rectangle();

	private Class<T> itemRenderer;

	private ArrayList<T> itemRenderers;
	private ArrayList<Object> rendererData;

	private DisplayObjectContainer container;
	private Quad background;
	
	public ItemList()
	{
		itemRenderers = new ArrayList<>();
		rendererData = new ArrayList<>();

		background = new Quad(10, 10, 0xFFFFFF);
		background.setAlpha(0);
		
		clipRect = new Rectangle();
		
		container = new DisplayObjectContainer();
		addChild(background);
		addChild(container);
		
		addTouchListener(this);
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

//	@Override
//	protected void onWorldMatrixUpdated()
//	{
//		getPosition(false, HELPER_VEC2);
//		clipRect.x = HELPER_VEC2.x;
//		clipRect.y = HELPER_VEC2.y;
//		clipRect.width = clipRect.x + background.getWidth();
//		clipRect.height = clipRect.y + background.getHeight();
//	}
	
	private void onDataChanged()
	{
		float xPos = 0;
		
		for (Object data : rendererData)
		{
			T renderer = ListRendererFactory.createFrom(itemRenderer);
			itemRenderers.add(renderer);
			
			renderer.setData(data);
			
			container.addChild(renderer);
			
			renderer.setX(xPos);
			xPos += renderer.getWidth();
		}
	}

	@Override
	public void setWidth(float value)
	{
		background.setWidth(value);
		clipRect.width = value;
	}

	@Override
	public void setHeight(float value)
	{
		background.setHeight(value);
		clipRect.height = value;
	}

	@Override
	public void onTouched(TouchState state, boolean collided, TouchData data)
	{
		if (state == TouchState.DRAG)
		{
			float x = container.getX();
			container.setX(x + data.dX);
		}
	}
}

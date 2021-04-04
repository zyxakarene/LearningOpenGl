package zyx.engine.components.screen.base.generic.window.scroll;

import zyx.engine.components.screen.base.events.types.mouse.IMouseDownListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseDraggedListener;
import zyx.engine.components.screen.base.events.types.mouse.MouseEvent;
import zyx.engine.components.screen.base.generic.window.WindowsButton;

public class WindowsScrollButton extends WindowsButton
{

	private IScrollViewScrolled scrollView;

	private int currentY;
	private int min;
	private int max;
	private float minGlobalMouseY;
	private float maxGlobalMouseY;

	private IMouseDownListener downListener;
	private IMouseDraggedListener dragListener;

	public WindowsScrollButton(IScrollViewScrolled scrollView)
	{
		this.scrollView = scrollView;

		downListener = this::onScrollerMouseDown;
		dragListener = this::onScrollerMouseDragged;

		addListener(downListener);
		addListener(dragListener);
	}

	void setLimits(int minScroll, int maxScroll)
	{
		min = minScroll;
		max = maxScroll;
	}

	void setScroll(float value)
	{
	}

	private void onScrollerMouseDown(MouseEvent event)
	{
		if (min == 0 && max == 0)
		{
			return;
		}

		HELPER_VEC2.setY(position.y - min);
		localToGlobal(HELPER_VEC2, HELPER_VEC2);

		minGlobalMouseY = -HELPER_VEC2.y;
		maxGlobalMouseY = minGlobalMouseY + max + min;

		HELPER_VEC2.setY(event.y);
		globalToLocal(HELPER_VEC2, HELPER_VEC2);

		minGlobalMouseY += HELPER_VEC2.y;
		maxGlobalMouseY -= (32 - HELPER_VEC2.y);

		currentY = (int) position.y;
	}

	private void onScrollerMouseDragged(MouseEvent event)
	{
		if (min == 0 && max == 0)
		{
			return;
		}
		
		int change = 0;
		if (event.y < minGlobalMouseY)
		{
			change = min - currentY;
			currentY = min;
		}
		else if (event.y > maxGlobalMouseY)
		{
			change = max - currentY;
			currentY = max;
		}
		else
		{
			change = (int) event.dY;
			currentY -= change;

			if (currentY < min)
			{
				change -= (min - currentY);
				currentY = min;
			}
			else if (currentY > max)
			{
				change += (currentY - max);
				currentY = max;
			}
		}

		if (change != 0)
		{
			setY(currentY);
			scrollView.onScrolled(currentY);
		}
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		downListener = null;
		dragListener = null;
	}
}

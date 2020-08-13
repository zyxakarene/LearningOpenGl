package zyx.engine.components.screen.base.generic.window.scroll;

import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.touch.ITouched;
import zyx.engine.touch.TouchData;
import zyx.engine.touch.TouchState;

public class WindowsScrollButton extends WindowsButton implements ITouched
{

	private IScrollViewScrolled scrollView;
	
	private int currentY;
	private int min;
	private int max;
	private float minGlobalMouseY;
	private float maxGlobalMouseY;

	public WindowsScrollButton(IScrollViewScrolled scrollView)
	{
		this.scrollView = scrollView;
		
		addTouchListener(this);
	}

	void setLimits(int minScroll, int maxScroll)
	{
		min = minScroll;
		max = maxScroll;
	}

	void setScroll(float value)
	{
	}

	@Override
	public void onTouched(TouchState state, boolean collided, TouchData data)
	{
		if (state == TouchState.DOWN)
		{
			HELPER_VEC2.setY(position.y - min);
			localToGlobal(HELPER_VEC2, HELPER_VEC2);
			
			minGlobalMouseY = -HELPER_VEC2.y;
			maxGlobalMouseY = minGlobalMouseY + max + min;
			
			HELPER_VEC2.setY(data.y);
			globalToLocal(HELPER_VEC2, HELPER_VEC2);
			
			minGlobalMouseY += HELPER_VEC2.y;
			maxGlobalMouseY -= (32 - HELPER_VEC2.y);
			
			currentY = (int) position.y;
		}
		else if (state == TouchState.DRAG)
		{
			if (data.y < minGlobalMouseY)
			{
				currentY = min;
			}
			else if (data.y > maxGlobalMouseY)
			{
				currentY = max;
			}
			else
			{
				currentY -= (int) data.dY;

				if (currentY < min)
				{
					currentY = min;
				}
				else if (currentY > max)
				{
					currentY = max;
				}
			}
			
			setY(currentY);
		}
	}
}

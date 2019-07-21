package zyx.engine.components.screen.base.generic;

import zyx.engine.components.screen.base.DisplayObject;

public class HList extends AbstractList
{
	@Override
	protected void sortItems()
	{
		float xPos = 0;
		
		DisplayObject child;
		int childrenCount = numChilren();
		for (int i = 0; i < childrenCount; i++)
		{
			child = getChildAt(i);
			child.setPosition(true, xPos, 0);
			
			xPos += child.getWidth();
		}
	}
}

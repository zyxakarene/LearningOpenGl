package zyx.engine.components.screen.base.generic;

import zyx.engine.components.screen.base.DisplayObject;

public class VList extends AbstractList
{
	@Override
	protected void sortItems()
	{
		float yPos = 0;
		
		DisplayObject child;
		int childrenCount = numChilren();
		for (int i = 0; i < childrenCount; i++)
		{
			child = getChildAt(i);
			child.setPosition(true, 0, yPos);
			
			yPos += child.getHeight();
		}
	}
}

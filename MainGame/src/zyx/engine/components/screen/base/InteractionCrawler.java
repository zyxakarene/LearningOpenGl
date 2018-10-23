package zyx.engine.components.screen.base;

import java.util.LinkedList;
import zyx.engine.touch.MouseTouchManager;

public class InteractionCrawler
{
	private LinkedList<DisplayObject> objects;
	private DisplayObjectContainer parent;
	
	private DisplayObject hitTarget;
	
	public InteractionCrawler(DisplayObjectContainer container)
	{
		parent = container;
		objects = new LinkedList<>();
	}

	public void interactionTest(int x, int y)
	{
		hitTarget = null;
		
		objects.clear();
		
		objects.add(parent);
		parent.getChildren(objects);
		
		DisplayObject child;
		DisplayObjectContainer childContainer;
		boolean hit;
		while (objects.size() > 0)
		{			
			child = objects.removeLast();
			
			if (child instanceof DisplayObjectContainer)
			{
				childContainer = (DisplayObjectContainer) child;
				childContainer.getChildren(objects);
				
				if (child.focusable == false)
				{
					continue;
				}
			}
			
			hit = child.hitTest(x, y);
			
			if (hit)
			{
				objects.clear();
				hitTarget = child;
			}
		}
		
		MouseTouchManager.getInstance().setTouchedObject(hitTarget);
	}
}

package zyx.engine.components.screen.base;

import java.util.LinkedList;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;

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
		objects.clear();
		
		objects.add(parent);
		parent.getChildren(objects);
		
		DisplayObject child;
		DisplayObjectContainer childContainer;
		boolean hit = false;
		while (objects.size() > 0)
		{			
			child = objects.removeLast();
			
			if (child instanceof DisplayObjectContainer)
			{
				childContainer = (DisplayObjectContainer) child;
				childContainer.getChildren(objects);
				
				if (((DisplayObjectContainer) child).focusable == false)
				{
					continue;
				}
			}
			
			hit = child.hitTest(x, y);
			
			if (hit)
			{
				if (hitTarget != null && hitTarget != child)
				{
					onTargetHitRemoved(hitTarget, x, y);
				}
				
				objects.clear();
				hitTarget = child;
				
				onTargetHit(hitTarget, x, y);
			}
		}
		
		if (!hit && hitTarget != null)
		{
			if (!hitTarget.disposed)
			{
				onTargetHitRemoved(hitTarget, x, y);
			}
			hitTarget = null;
		}
	}

	private void onTargetHitRemoved(DisplayObject target, int x, int y)
	{
		CursorManager.getInstance().setCursor(GameCursor.POINTER);
		
		target.dispatchTouch(false, x, y);
//		if (target instanceof InteractableContainer)
//		{
//			((InteractableContainer) target).updateButtonState(false);
//		}
	}
	
	private void onTargetHit(DisplayObject target, int x, int y)
	{
		target.dispatchTouch(true, x, y);
//		if (target instanceof InteractableContainer)
//		{
//			((InteractableContainer) target).updateButtonState(true);
//		}
//		
//		if (target instanceof IFocusable && MouseData.data.isLeftClicked())
//		{
//			Stage.instance.setFocusedObject((IFocusable) target);
//		}
	}
	
}

package zyx.engine.components.screen;

import java.util.LinkedList;
import org.lwjgl.input.Keyboard;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.MouseData;

class InteractionCrawler
{
	private LinkedList<DisplayObject> objects;
	private DisplayObjectContainer parent;
	
	private DisplayObject hitTarget;
	
	InteractionCrawler(DisplayObjectContainer container)
	{
		parent = container;
		objects = new LinkedList<>();
	}

	void interactionTest(int x, int y)
	{
		objects.clear();
		
		parent.getChildren(objects);
		
		DisplayObject child;
		DisplayObjectContainer childContainer;
		boolean hit = false;
		while (objects.size() > 0)
		{			
			child = objects.removeLast();
			
			if (KeyboardData.data.wasPressed(Keyboard.KEY_0))
			{
				System.out.println("");
			}
			
			if (child instanceof DisplayObjectContainer)
			{
				childContainer = (DisplayObjectContainer) child;
				childContainer.getChildren(objects);
			}
			
			hit = child.hitTest(x, y);
			
			if (hit)
			{
				if (hitTarget != null && hitTarget != child)
				{
					onTargetHitRemoved(hitTarget);
				}
				
				objects.clear();
				hitTarget = child;
				
				onTargetHit(hitTarget);
			}
		}
		
		if (!hit && hitTarget != null)
		{
			if (!hitTarget.disposed)
			{
				onTargetHitRemoved(hitTarget);
			}
			hitTarget = null;
		}
	}

	private void onTargetHitRemoved(DisplayObject target)
	{
		CursorManager.getInstance().setCursor(GameCursor.POINTER);
		
		if (target instanceof InteractableContainer)
		{
			((InteractableContainer) target).updateButtonState(false);
		}
	}
	
	private void onTargetHit(DisplayObject target)
	{
		if (target.buttonMode)
		{
			CursorManager.getInstance().setCursor(GameCursor.HAND);
		}
		
		if (target instanceof InteractableContainer)
		{
			((InteractableContainer) target).updateButtonState(true);
		}
		
		if (target.focusable && MouseData.data.isLeftDown())
		{
			Stage.instance.setFocusedObject(target);
		}
	}
	
}

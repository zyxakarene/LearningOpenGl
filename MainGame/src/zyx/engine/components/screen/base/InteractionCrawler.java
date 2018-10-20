package zyx.engine.components.screen.base;

import java.util.LinkedList;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.game.controls.input.MouseData;
import zyx.utils.cheats.Print;

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
			
			if (child.name != null)
			{
				if (child.name.equals("breakpoint"))
				{
					Print.out("Testing", child.name);
					Vector2f position = child.getPosition(false, null);
					Vector2f scale = child.getScale(false, null);
					float w = child.getWidth();
					float h = child.getHeight();
					Print.out(scale, position, w, h);
				}
			}
			
			hit = child.hitTest(x, y);
			
			if (hit)
			{
				child.hitTest(x, y);
				
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
		if (target.hoverIcon != null)
		{
			CursorManager.getInstance().setCursor(target.hoverIcon);
		}
		
		if (target instanceof InteractableContainer)
		{
			((InteractableContainer) target).updateButtonState(true);
		}
		
		if (target instanceof IFocusable && MouseData.data.isLeftClicked())
		{
			Stage.instance.setFocusedObject((IFocusable) target);
		}
	}
	
}

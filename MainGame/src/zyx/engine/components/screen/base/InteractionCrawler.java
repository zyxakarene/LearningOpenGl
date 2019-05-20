package zyx.engine.components.screen.base;

import java.util.LinkedList;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.touch.MouseTouchManager;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.geometry.Rectangle;

public class InteractionCrawler
{

	private LinkedList<DisplayObject> objects;
	private DisplayObjectContainer parent;

	private DisplayObject hitTarget;

	private Rectangle clipRect;
	private Rectangle clipRectBackup;
	private Vector2f helperVec;

	public InteractionCrawler(DisplayObjectContainer container)
	{
		parent = container;
		objects = new LinkedList<>();
		clipRect = new Rectangle();
		clipRectBackup = new Rectangle();
		helperVec = new Vector2f();
	}

	public void interactionTest(int x, int y)
	{
		hitTarget = null;
		clipRect.x = 0;
		clipRect.y = 0;
		clipRect.width = GameConstants.GAME_WIDTH;
		clipRect.height = GameConstants.GAME_HEIGHT;

		objects.clear();

		objects.add(parent);
		parent.getChildren(objects);

		DisplayObject child;
		DisplayObjectContainer childContainer;
		boolean hit;
		boolean insideClip;
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

			if (child.clipRect != null)
			{
				clipRectBackup.copyFrom(clipRect);
				child.getPosition(true, helperVec);
				clipRect.x = FloatMath.max(clipRect.x, child.clipRect.x + helperVec.x);
				clipRect.y = FloatMath.max(clipRect.y, child.clipRect.y + helperVec.y);
				clipRect.width = FloatMath.min(clipRect.width, child.clipRect.x + helperVec.x + child.clipRect.width);
				clipRect.height = FloatMath.min(clipRect.height, child.clipRect.y + helperVec.y + child.clipRect.height);
			}

			hit = clipTest(x, y) && child.hitTest(x, y);

			if (child.clipRect != null)
			{
				clipRect.copyFrom(clipRectBackup);
			}

			if (hit)
			{
				objects.clear();
				hitTarget = child;
			}
		}

		MouseTouchManager.getInstance().setTouchedObject(hitTarget);
	}

	private boolean clipTest(int x, int y)
	{
		return clipRect.x <= x &&
				clipRect.y <= y &&
				clipRect.x + clipRect.width >= x &&
				clipRect.y + clipRect.height >= y;
	}
}
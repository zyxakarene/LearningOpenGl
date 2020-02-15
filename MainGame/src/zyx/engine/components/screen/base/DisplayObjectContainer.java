package zyx.engine.components.screen.base;

import java.util.ArrayList;
import java.util.List;

public class DisplayObjectContainer extends DisplayObject
{

	private ArrayList<DisplayObject> children;
	private int numChildren;

	public float forceWidth = -1;
	public float forceHeight = -1;
	
	public DisplayObjectContainer()
	{
		children = new ArrayList<>();
		numChildren = 0;
	}

	@Override
	protected void updateTransforms(boolean alsoChildren)
	{
		super.updateTransforms(alsoChildren);
		
		if (alsoChildren)
		{
			for (DisplayObject child : children)
			{
				child.updateTransforms(alsoChildren);
			}
		}
	}
	
	public DisplayObject getChildAt(int index)
	{
		return children.get(index);
	}

	public void getChildren(List<DisplayObject> out)
	{
		out.addAll(children);
	}
	
	public void addChild(DisplayObject child)
	{
		if (child == this)
		{
			throw new IllegalArgumentException("Cannot set DisplayObject as child of self");
		}
		
		DisplayObjectContainer prevParent = child.getParent();
		if (prevParent != null)
		{
			prevParent.removeChild(child);
		}
		
		child.setParent(this);
		
		children.add(child);
		numChildren++;
		
		childAdded(child);
	}

	public boolean removeChild(int index)
	{
		DisplayObject child = children.get(index);
		return removeChild(child);
	}

	public boolean removeChild(DisplayObject child)
	{
		if(child.getParent() == this)
		{
			children.remove(child);
			numChildren--;
			
			child.setParent(null);
			
			childRemoved(child);
			
			return true;
		}
		else
		{
			String msg = String.format("Cannot remove child %s when its parent %s != this %s", child, child.getParent(), this);
			throw new RuntimeException(msg);
		}
	}
	
	public void removeChildren(boolean dispose)
	{
		int len = children.size();
		DisplayObject child;
		for (int i = len - 1; i >= 0; i--)
		{
			child = children.get(i);
			removeChild(child);
			
			if (dispose)
			{
				child.dispose();
			}
		}
		
		children.clear();
	}

	public int numChilren()
	{
		return numChildren;
	}

	@Override
	public float getWidth()
	{
		if (forceWidth != -1)
		{
			return forceWidth;
		}
		
		float mostLeft = 0f;
		float mostRight = 0f;

		float left, right;
		DisplayObject loopHelper;
		for (int i = 0; i < numChildren; i++)
		{
			loopHelper = children.get(i);
			left = loopHelper.position.x;
			right = loopHelper.position.x + loopHelper.getWidth();

			if (left < mostLeft)
			{
				mostLeft = left;
			}

			if (right > mostRight)
			{
				mostRight = right;
			}
		}

		getScale(true, HELPER_VEC2);
		return (mostRight - mostLeft) * HELPER_VEC2.x;
	}

	@Override
	public float getHeight()
	{
		if (forceHeight != -1)
		{
			return forceHeight;
		}
		
		float mostUp = 0f;
		float mostDown = 0f;

		float up, down;
		DisplayObject loopHelper;
		for (int i = 0; i < numChildren; i++)
		{
			loopHelper = children.get(i);
			up = loopHelper.position.y;
			down = loopHelper.position.y + loopHelper.getHeight();

			if (up < mostUp)
			{
				mostUp = up;
			}

			if (down > mostDown)
			{
				mostDown = down;
			}
		}

		getScale(true, HELPER_VEC2);
		return (mostDown - mostUp) * HELPER_VEC2.y;
	}

	@Override
	public void setWidth(float value)
	{
		getScale(true, HELPER_VEC2);
		
		float scaleY = HELPER_VEC2.y;
		
		setScale(value / getWidth(), scaleY);
	}

	@Override
	public void setHeight(float value)
	{
		getScale(true, HELPER_VEC2);
		
		float scaleX = HELPER_VEC2.x;
		
		setScale(scaleX, value / getHeight());
	}

	@Override
	protected void onDraw()
	{
		DisplayObject loopHelper;
		for (int i = 0; i < numChildren; i++)
		{
			loopHelper = children.get(i);

			loopHelper.draw();
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();

		for (int i = numChildren - 1; i >= 0; i--)
		{
			children.get(i).dispose();
		}

		children.clear();
		children = null;
	}
	
	protected final void childAdded(DisplayObject child)
	{
		DisplayObjectContainer parent = getParent();
		if (parent != null)
		{
			parent.childAdded(child);
		}
		
		onChildAdded(child);
	}
	
	protected final void childRemoved(DisplayObject child)
	{
		DisplayObjectContainer parent = getParent();
		if (parent != null)
		{
			parent.childRemoved(child);
		}
		
		onChildRemoved(child);
	}
	
	protected void onChildAdded(DisplayObject child)
	{
		
	}
	
	protected void onChildRemoved(DisplayObject child)
	{
		
	}

	public void flipChildren(int indexA, int indexB)
	{
		DisplayObject a = children.get(indexA);
		DisplayObject b = children.get(indexB);
		
		children.set(indexA, b);
		children.set(indexB, a);
	}
}

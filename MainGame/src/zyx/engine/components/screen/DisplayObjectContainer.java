package zyx.engine.components.screen;

import java.util.ArrayList;
import java.util.List;

public class DisplayObjectContainer extends DisplayObject
{

	private ArrayList<DisplayObject> children;
	private int numChildren;

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
		DisplayObjectContainer prevParent = child.getParent();
		if (prevParent != null)
		{
			prevParent.removeChild(child);
		}
		
		child.setParent(this);
		
		children.add(child);
		numChildren++;
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
		for (int i = 0; i < len; i++)
		{
			child = children.get(i);
			removeChild(child);
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

		return mostRight - mostLeft;
	}

	@Override
	public float getHeight()
	{
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

		return mostDown - mostUp;
	}

	@Override
	public void setWidth(float value)
	{
		getScale(true, HELPER_VEC2);
		
		setScale(value / getWidth(), HELPER_VEC2.y);
	}

	@Override
	public void setHeight(float value)
	{
		getScale(true, HELPER_VEC2);
		
		setScale(HELPER_VEC2.x, value / getHeight());
	}

	@Override
	void onDraw()
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
}

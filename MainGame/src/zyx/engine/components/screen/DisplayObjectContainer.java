package zyx.engine.components.screen;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.implementations.ScreenShader;

public class DisplayObjectContainer extends DisplayObject
{
	private static DisplayObject loopHelper;
	
	private ArrayList<DisplayObject> children;
	private int numChildren;

	public DisplayObjectContainer()
	{
		children = new ArrayList<>();
		numChildren = 0;
	}
	
	public DisplayObject getChildAt(int index)
	{
		return children.get(index);
	}
	
	public void addChild(DisplayObject child)
	{
		children.add(child);
		numChildren++;
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
		for (int i = 0; i < numChildren; i++)
		{
			loopHelper = children.get(i);
			left = loopHelper.position.x;
			right = loopHelper.position.x + loopHelper.getWidth();
			
			if(left < mostLeft)
			{
				mostLeft = left;
			}
			
			if(right > mostRight)
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
		for (int i = 0; i < numChildren; i++)
		{
			loopHelper = children.get(i);
			up = loopHelper.position.y;
			down = loopHelper.position.y + loopHelper.getHeight();
			
			if(up < mostUp)
			{
				mostUp = up;
			}
			
			if(down > mostDown)
			{
				mostDown = down;
			}
		}
		
		return mostDown - mostUp;
	}
	
	private final Matrix4f backupMatrix = new Matrix4f();
	
	@Override
	final void draw()
	{
		transform();
		backupMatrix.load(ScreenShader.MATRIX_MODEL);
		for (int i = 0; i < numChildren; i++)
		{
			loopHelper = children.get(i);
			
			shader.upload();
			
			loopHelper.draw();
			
			ScreenShader.MATRIX_MODEL.load(backupMatrix);
		}
	}
	
	
}

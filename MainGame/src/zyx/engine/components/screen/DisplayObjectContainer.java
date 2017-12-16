package zyx.engine.components.screen;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.input.MouseData;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.utils.cheats.Print;

public class DisplayObjectContainer extends DisplayObject
{

	private final Matrix4f HELPER_MATRIX = new Matrix4f();

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
	void onDraw()
	{
		transform();
		HELPER_MATRIX.load(ScreenShader.MATRIX_MODEL);

		DisplayObject loopHelper;
		for (int i = 0; i < numChildren; i++)
		{
			loopHelper = children.get(i);

			shader.upload();

			loopHelper.draw();

			ScreenShader.MATRIX_MODEL.load(HELPER_MATRIX);
		}
	}

	protected final void checkClicks(boolean hasCollision)
	{
		DisplayObject loopHelper;
		for (int i = numChildren - 1; i >= 0; i--)
		{
			loopHelper = children.get(i);
			if (loopHelper instanceof InteractableContainer)
			{
				hasCollision = ((InteractableContainer) loopHelper).hasMouseCollision(hasCollision) || hasCollision;
			}
			else if (loopHelper instanceof DisplayObjectContainer)
			{
				((DisplayObjectContainer) loopHelper).checkClicks(hasCollision);
			}
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();

		for (int i = 0; i < numChildren; i++)
		{
			children.get(i).dispose();
		}

		children.clear();
		children = null;
	}

}

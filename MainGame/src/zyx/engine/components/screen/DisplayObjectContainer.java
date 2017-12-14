package zyx.engine.components.screen;

import java.awt.Polygon;
import java.awt.Shape;
import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.MouseControl;
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
	final void draw()
	{
		transform();
		HELPER_MATRIX.load(ScreenShader.MATRIX_MODEL);
		DisplayObject loopHelper;
		for (int i = 0; i < numChildren; i++)
		{
			loopHelper = children.get(i);

			shader.upload();

			loopHelper.draw();
			if (loopHelper instanceof IClickable)
			{
				Vector4f mousePos = new Vector4f(MouseControl.getPosX(), -MouseControl.getPosY(), -1, 1);
				
				Matrix4f inverse = new Matrix4f(ScreenShader.MATRIX_MODEL);
				inverse.invert();
				
				Print.out("Mouse on screen at:", mousePos.x, mousePos.y);
				Matrix4f.transform(inverse, mousePos, mousePos);
				Print.out("Mouse transformed to:", mousePos.x, mousePos.y);
				
				if (mousePos.x >= 0 && mousePos.y <= 0 && mousePos.x <= 100 && mousePos.y >= -100)
				{
					loopHelper.rotation++;
				}
				
				
				Vector4f pos = new Vector4f(0, 0, -1, 1);
				Vector4f corner = new Vector4f(100, -100, -1, 1);
				Matrix4f.transform(ScreenShader.MATRIX_MODEL, corner, corner);
				Matrix4f.transform(ScreenShader.MATRIX_MODEL, pos, pos);
				
				

				Print.out("Container:", this, loopHelper, "was drawn at", ScreenShader.MATRIX_MODEL.m30, -ScreenShader.MATRIX_MODEL.m31);
				Print.out("Container:", this, loopHelper, "has calculated pos at", pos);
				Matrix4f.transform(inverse, corner, corner);
				Print.out("Container:", this, loopHelper, "reverted to", corner);
				Print.out("Container:", this, loopHelper, "has corner", corner);
			}
			
			Polygon s = new Polygon();


			ScreenShader.MATRIX_MODEL.load(HELPER_MATRIX);
		}
	}

}

package zyx.engine.components.screen;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.SharedPools;
import zyx.game.controls.input.MouseData;
import zyx.opengl.shaders.implementations.ScreenShader;

public abstract class InteractableContainer extends DisplayObjectContainer
{

	private static final Vector4f MOUSE_POS_HELPER = new Vector4f(0, 0, -1, 1);
	private Matrix4f invertedModel;

	private boolean wasMouseOver;
	private boolean wasMouseDown;

	public InteractableContainer()
	{
		invertedModel = SharedPools.MATRIX_POOL.getInstance();
	}

	@Override
	public void dispose()
	{
		super.dispose();

		SharedPools.MATRIX_POOL.releaseInstance(invertedModel);
		invertedModel = null;
	}

	@Override
	void onDraw()
	{
		super.onDraw();
		Matrix4f.invert(ScreenShader.MATRIX_MODEL, invertedModel);

		MOUSE_POS_HELPER.x = MouseData.instance.x;
		MOUSE_POS_HELPER.y = -MouseData.instance.y;
		
		Matrix4f.transform(invertedModel, MOUSE_POS_HELPER, MOUSE_POS_HELPER);

		boolean mouseCollision = MOUSE_POS_HELPER.x >= 0   && MOUSE_POS_HELPER.y <= 0 && 
								 MOUSE_POS_HELPER.x <= 100 && MOUSE_POS_HELPER.y >= -100;
		if(!mouseCollision && MouseData.instance.isLeftClicked())
		{
			wasMouseDown = false;
			wasMouseOver = false;
		}
		
		if (!wasMouseOver && mouseCollision)
		{
			if (wasMouseDown)
			{
				onMouseDown();
			}
			else
			{
				onMouseEnter();
			}
			wasMouseOver = true;
		}
		else if (wasMouseOver && !mouseCollision)
		{
			onMouseExit();
			wasMouseOver = false;
		}

		if (mouseCollision)
		{
			if (!wasMouseDown && MouseData.instance.isLeftDown())
			{
				onMouseDown();
				wasMouseDown = true;
			}
			else if (wasMouseDown && !MouseData.instance.isLeftDown())
			{
				onMouseClick();
				wasMouseDown = false;
			}
		}
	}

	protected abstract void onMouseEnter();

	protected abstract void onMouseExit();

	protected abstract void onMouseDown();
	
	protected abstract void onMouseClick();
}

package zyx.engine.components.screen;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.SharedPools;
import zyx.game.controls.input.MouseData;
import zyx.opengl.shaders.SharedShaderObjects;

public abstract class InteractableContainer extends DisplayObjectContainer
{

	private static final Vector4f MOUSE_POS_HELPER = new Vector4f(0, 0, -1, 1);
	private Matrix4f invertedModel;

	private boolean wasMouseOver;
	private boolean wasMouseDown;
	private boolean wasMouseDownOutside;

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

	protected boolean hasMouseCollision(boolean hasCollided)
	{
		if (hasCollided)
		{
			updateButtonState(false);
			return false;
		}
		else
		{
			MOUSE_POS_HELPER.x = MouseData.data.x;
			MOUSE_POS_HELPER.y = -MouseData.data.y;

			Matrix4f.transform(invertedModel, MOUSE_POS_HELPER, MOUSE_POS_HELPER);

			boolean collision = MOUSE_POS_HELPER.x >= 0 && MOUSE_POS_HELPER.y <= 0 && MOUSE_POS_HELPER.x <= getQuadWidth() && MOUSE_POS_HELPER.y >= -getQuadHeight();

			updateButtonState(collision && !hasCollided);

			return collision;
		}
	}

	@Override
	void onDraw()
	{
		super.onDraw();
		Matrix4f.invert(SharedShaderObjects.SHARED_MODEL_TRANSFORM, invertedModel);
	}

	protected abstract void onMouseEnter();

	protected abstract void onMouseExit();

	protected abstract void onMouseDown();

	protected abstract void onMouseClick();

	protected abstract float getQuadWidth();

	protected abstract float getQuadHeight();

	private void updateButtonState(boolean mouseCollision)
	{
		boolean isLeftDown = MouseData.data.isLeftDown();

		if (!wasMouseOver && isLeftDown)
		{
			wasMouseDownOutside = true;
		}

		if (!wasMouseOver && mouseCollision)
		{
			wasMouseOver = true;
			onMouseEnter();
		}
		else if (wasMouseOver && !mouseCollision)
		{
			wasMouseOver = false;
			onMouseExit();
		}

		if (!wasMouseDownOutside && mouseCollision)
		{
			if (!wasMouseDown && isLeftDown)
			{
				onMouseDown();
				wasMouseDown = true;
			}
			else if (wasMouseDown && !isLeftDown)
			{
				onMouseClick();
				wasMouseDown = false;
			}
		}

		if (isLeftDown == false)
		{
			wasMouseDownOutside = false;
			wasMouseDown = false;
		}	}

}

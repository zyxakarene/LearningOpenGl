package zyx.engine.components.screen.interactable;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.game.controls.input.MouseData;

public abstract class InteractableContainer extends DisplayObjectContainer
{

	private boolean wasMouseOver;
	private boolean wasMouseDown;
	private boolean wasMouseDownOutside;

	public InteractableContainer()
	{
	}

	protected abstract void onMouseEnter();

	protected abstract void onMouseExit();

	protected abstract void onMouseDown();

	protected abstract void onMouseClick();

	public void updateButtonState(boolean mouseCollision)
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
		}
	}
}

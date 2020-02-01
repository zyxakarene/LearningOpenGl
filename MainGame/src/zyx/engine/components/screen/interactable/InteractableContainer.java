package zyx.engine.components.screen.interactable;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.touch.ITouched;
import zyx.engine.touch.TouchData;
import zyx.engine.touch.TouchState;
import zyx.game.controls.input.MouseData;
import zyx.utils.cheats.Print;

public abstract class InteractableContainer extends DisplayObjectContainer
{

	private boolean wasMouseOver;
	private boolean wasMouseDown;
	private boolean wasMouseDownOutside;
	
	private ITouched touchListener;

	public InteractableContainer()
	{
		touchListener = (TouchState state, boolean collided, TouchData data) ->
		{
			if (focusable)
			{
				updateButtonState(collided);
			}
		};
		
		addTouchListener(touchListener);
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

	@Override
	public void dispose()
	{
		super.dispose();
		
		if(touchListener != null)
		{
			removeTouchListener(touchListener);
			touchListener = null;
		}
	}
}

package zyx.engine.components.screen.interactable;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.events.types.touch.*;

public abstract class InteractableContainer extends DisplayObjectContainer
{

	private IMouseClickedListener clickListener;
	private IMouseDownListener downListener;
	private IMouseDraggedListener dragListener;
	private IMouseEnteredListener enterListener;
	private IMouseExitedListener exitListener;
	private IMouseUpListener upListener;

	public InteractableContainer()
	{
		clickListener = this::onMouseClick;
		downListener = this::onMouseDown;
		dragListener = this::onMouseDragged;
		enterListener = this::onMouseEnter;
		exitListener = this::onMouseExit;
		upListener = this::onMouseUp;
		
		addListener(clickListener);
		addListener(downListener);
		addListener(dragListener);
		addListener(enterListener);
		addListener(exitListener);
		addListener(upListener);
	}

	protected void onMouseEnter(TouchEvent event)
	{
	}

	protected void onMouseExit(TouchEvent event)
	{
	}

	protected void onMouseDown(TouchEvent event)
	{
	}

	protected void onMouseUp(TouchEvent event)
	{
	}

	protected void onMouseClick(TouchEvent event)
	{
	}

	protected void onMouseDragged(TouchEvent event)
	{
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}
}

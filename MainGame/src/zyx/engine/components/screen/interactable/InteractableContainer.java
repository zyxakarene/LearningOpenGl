package zyx.engine.components.screen.interactable;

import zyx.engine.components.screen.base.events.types.mouse.IMouseUpListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseDownListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseClickedListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseExitedListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseDraggedListener;
import zyx.engine.components.screen.base.events.types.mouse.MouseEvent;
import zyx.engine.components.screen.base.events.types.mouse.IMouseEnteredListener;
import zyx.engine.components.screen.base.DisplayObjectContainer;

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

	protected void onMouseEnter(MouseEvent event)
	{
	}

	protected void onMouseExit(MouseEvent event)
	{
	}

	protected void onMouseDown(MouseEvent event)
	{
	}

	protected void onMouseUp(MouseEvent event)
	{
	}

	protected void onMouseClick(MouseEvent event)
	{
	}

	protected void onMouseDragged(MouseEvent event)
	{
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}
}

package zyx.engine.components.screen.interactable;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.events.types.touch.ITouchedListener;
import zyx.engine.components.screen.base.events.types.touch.TouchEvent;

public abstract class InteractableContainer extends DisplayObjectContainer
{

	private InteractableTouchAdaptor touchedAdaptor;

	public InteractableContainer()
	{
		touchedAdaptor = new InteractableTouchAdaptor(this);
		addListener(touchedAdaptor);
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
		if (touchedAdaptor != null)
		{
			removeListener(touchedAdaptor);
			touchedAdaptor = null;
		}

		super.dispose();
	}

	private static class InteractableTouchAdaptor implements ITouchedListener
	{

		private final InteractableContainer container;

		private InteractableTouchAdaptor(InteractableContainer container)
		{
			this.container = container;
		}

		@Override
		public void mouseEnter(TouchEvent event)
		{
			container.onMouseEnter(event);
		}

		@Override
		public void mouseExit(TouchEvent event)
		{
			container.onMouseExit(event);
		}

		@Override
		public void mouseDown(TouchEvent event)
		{
			container.onMouseDown(event);
		}

		@Override
		public void mouseUp(TouchEvent event)
		{
			container.onMouseUp(event);
		}

		@Override
		public void mouseClick(TouchEvent event)
		{
			container.onMouseClick(event);
		}

		@Override
		public void mouseDragged(TouchEvent event)
		{
			container.onMouseDragged(event);
		}

	}
}

package zyx.engine.components.screen.base.generic.window;

import zyx.engine.components.screen.base.DraggableComponent;
import zyx.engine.components.screen.base.generic.Panel;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.geometry.Rectangle;

public class Window extends Panel implements ICallback<InteractableContainer>
{
	private WindowsTitleBar title;
	private DraggableComponent draggable;
	
	private WindowsButton minimizeButton;
	private boolean shown;


	public Window(int width, int height)
	{
		super(width, height);
		
		title = new WindowsTitleBar(width);
		addChild(title);
		
		draggable = new DraggableComponent(title, this);
		
		clipRect = new Rectangle(0, 0, width, 32);
		shown = false;
		
		minimizeButton = new WindowsButton();
		minimizeButton.setPosition(true, 8, 8);
		minimizeButton.onButtonClicked.addCallback(this);
		addChild(minimizeButton);
	}

	@Override
	public void onCallback(InteractableContainer data)
	{
		shown = !shown;
		if (shown)
		{
			clipRect.height = getHeight();
		}
		else
		{
			clipRect.height = 32;
		}
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		if (draggable != null)
		{
			draggable.dispose();
			draggable = null;
		}
	}
}

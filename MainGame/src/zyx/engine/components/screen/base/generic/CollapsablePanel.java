package zyx.engine.components.screen.base.generic;

import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.geometry.Rectangle;

public class CollapsablePanel extends Panel implements ICallback<InteractableContainer>
{
	
	private Button minimizeButton;
	private boolean shown;
	
	public CollapsablePanel(int width, int height)
	{
		super(width, height);
		
		clipRect = new Rectangle(0, 0, width, 16);
		
		String[] textures = new String[]
		{
			"container", "container", "container_down"
		};
		minimizeButton = new Button(false);
		minimizeButton.setWidth(16);
		minimizeButton.setHeight(16);
		minimizeButton.setTextures(textures);
		minimizeButton.onButtonClicked.addCallback(this);
		addChild(minimizeButton);
	}

	@Override
	protected void updateTransforms(boolean alsoChildren)
	{
		super.updateTransforms(alsoChildren);
		
		getPosition(false, HELPER_VEC2);
		clipRect.x = HELPER_VEC2.x;
		clipRect.y = HELPER_VEC2.y - position.y;
		
		System.out.println(HELPER_VEC2);
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
			clipRect.height = 16;
		}
	}

}

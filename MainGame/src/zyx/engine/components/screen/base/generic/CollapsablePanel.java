package zyx.engine.components.screen.base.generic;

import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.geometry.Rectangle;

public class CollapsablePanel extends Panel implements ICallback<InteractableContainer>
{
	
	private Button minimizeButton;
	private boolean shown;
	
	public CollapsablePanel(int width, int height, int color)
	{
		super(width, height, color);
		
		clipRect = new Rectangle(0, 0, width, 10);
		
		String[] textures = new String[]
		{
			"texture.tile", "texture.tile", "texture.tile"
		};
		minimizeButton = new Button(false);
		minimizeButton.setWidth(32);
		minimizeButton.setHeight(32);
		minimizeButton.setTextures(textures);
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
			clipRect.height = 10;
		}
	}

}

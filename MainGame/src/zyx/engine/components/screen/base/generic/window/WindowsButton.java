package zyx.engine.components.screen.base.generic.window;

import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.interactable.Button;

public class WindowsButton extends Button
{

	private static final String[] TEXTURES = new String[]
	{
		"container",
	};

	public WindowsButton()
	{
		super(true);

		setWidth(256);
		setHeight(16);
		setColors(ComposedConstants.buttonColorsFromScheme("gray"));
		setTextures(TEXTURES);
	}

}

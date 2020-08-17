package zyx.engine.components.screen.base.generic.window;

import zyx.engine.components.screen.text.Textfield;

public class WindowsTextfield extends Textfield
{

	public WindowsTextfield(String text)
	{
		super(text);
		load(Textfield.DEFAULT_RESOURCE);
	}

	public WindowsTextfield()
	{
		this("");
	}

}

package zyx.engine.components.screen.base.generic.window;

import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.interactable.Button;

public class WindowsButton extends Button
{

	private static final String[] TEXTURES = new String[]
	{
		"container",
	};
	
	private WindowsTextfield label;

	public WindowsButton()
	{
		this("");
	}

	public WindowsButton(String text)
	{
		super(true);

		setWidth(16);
		setHeight(16);
		setColors(ComposedConstants.buttonColorsFromScheme("gray"));
		setTextures(TEXTURES);
		
		if (text.isEmpty() == false)
		{
			label = new WindowsTextfield(text);
			addChild(label);
			
			label.setSize(getWidth(), getHeight());
		}
	}
	
	public void setText(String text)
	{
		if (label == null)
		{
			label = new WindowsTextfield(text);
			addChild(label);
		}
		else
		{
			label.setText(text);
		}
	}

	@Override
	public void setWidth(float value)
	{
		super.setWidth(value);
		
		if (label != null)
		{
			label.setWidth(value);
		}
	}

	@Override
	public void setHeight(float value)
	{
		super.setHeight(value);
		
		if (label != null)
		{
			label.setHeight(value);
		}
	}
}

package zyx.engine.components.screen.base.generic.window;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.interactable.Checkbox;
import zyx.engine.curser.GameCursor;
import zyx.opengl.textures.bitmapfont.alignment.HAlignment;

public class WindowsCheckbox extends DisplayObjectContainer
{

	private static final String[] TEXTURES = new String[]
	{
		"checkbox",
	};

	private Checkbox check;
	private WindowsTextfield textfield;

	public WindowsCheckbox()
	{
		check = new Checkbox(false);
		check.setWidth(16);
		check.setHeight(16);
		check.setColors(ComposedConstants.buttonColorsFromScheme("gray"));
		check.setTextures(TEXTURES);
		check.loadCheckmark("checkmark");

		textfield = new WindowsTextfield("Do the thing?");
		textfield.setHAlign(HAlignment.LEFT);
		textfield.setPosition(true, 16 + 5, 0);
		textfield.setSize(100, 16);
		textfield.showBorders(true);

		addChild(check);
		addChild(textfield);
		
		Quad q = new Quad(100 + 16 + 5, 16, 0xFFFFFF);
		q.buttonMode = true;
		q.hoverIcon = GameCursor.HAND;
		q.focusable = true;
		q.setAlpha(0);
		
		check.addChild(q);
	}

}

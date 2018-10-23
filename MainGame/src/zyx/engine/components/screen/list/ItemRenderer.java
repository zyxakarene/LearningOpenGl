package zyx.engine.components.screen.list;

import zyx.engine.touch.ITouched;
import zyx.engine.curser.GameCursor;
import zyx.engine.touch.TouchData;
import zyx.engine.touch.TouchState;
import zyx.game.components.screen.json.JsonSprite;
import zyx.game.controls.input.MouseData;
import zyx.utils.geometry.Rectangle;

public abstract class ItemRenderer extends JsonSprite implements ITouched
{

	protected Object data;

	public ItemRenderer(boolean button)
	{
		super();

		if (button)
		{
			addTouchListener(this);

			buttonMode = true;
			hoverIcon = GameCursor.HAND;
		}
	}

	final void setClipRect(Rectangle clipRect)
	{
		this.clipRect = clipRect;
	}

	public final void setData(Object data)
	{
		this.data = data;
		onDataSet();
	}

	protected void onDataSet()
	{
	}

	@Override
	public void onTouched(TouchState state, boolean collided, TouchData data)
	{
	}

}

package zyx.engine.components.screen.list;

import zyx.engine.touch.ITouched;
import zyx.engine.curser.GameCursor;
import zyx.engine.touch.TouchData;
import zyx.engine.touch.TouchState;
import zyx.game.components.screen.json.JsonSprite;

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

	public final void setData(Object data)
	{
		this.data = data;
		onDataSet();
	}
	
	public Object getData()
	{
		return data;
	}

	protected void onDataSet()
	{
	}

	@Override
	public void onTouched(TouchState state, boolean collided, TouchData data)
	{
	}

}

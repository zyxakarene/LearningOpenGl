package zyx.engine.components.screen.list;

import zyx.engine.components.screen.base.events.types.touch.IMouseClickedListener;
import zyx.engine.components.screen.base.events.types.touch.TouchEvent;
import zyx.engine.curser.GameCursor;
import zyx.game.components.screen.json.JsonSprite;

public abstract class ItemRenderer extends JsonSprite
{

	protected Object data;
	
	private IMouseClickedListener clickedListener;

	public ItemRenderer(boolean button)
	{
		super();

		if (button)
		{
			hoverIcon = GameCursor.HAND;
			
			clickedListener = this::onMouseClicked;
			addListener(clickedListener);
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
	
	protected void onMouseClicked(TouchEvent event)
	{
		
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		clickedListener = null;
	}
}

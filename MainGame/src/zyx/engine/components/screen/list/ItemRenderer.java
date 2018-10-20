package zyx.engine.components.screen.list;

import zyx.engine.components.screen.base.Quad;
import zyx.game.components.screen.json.JsonSprite;

public class ItemRenderer extends JsonSprite
{
	protected Object data;
	private Quad quad;

	public ItemRenderer()
	{
		super();
	}
	
	
	@Override
	public String getResource()
	{
		return "json.renderer";
	}

	@Override
	protected void onComponentsCreated()
	{
		quad = this.<Quad>getComponentByName("renderer_quad");
	}
	
	public final void setData(Object data)
	{
		this.data = data;
		
		onDataSet();
	}

	protected void onDataSet()
	{
		quad.setColor((Integer) data);
	}
}

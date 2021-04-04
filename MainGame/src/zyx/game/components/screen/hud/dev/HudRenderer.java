package zyx.game.components.screen.hud.dev;

import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.list.ItemRenderer;

public class HudRenderer extends ItemRenderer
{

	private Quad quad;

	public HudRenderer()
	{
		super(true);
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
	
	@Override
	protected void onDataSet()
	{
		quad.setColor((Integer) data);
	}

//	@Override
	public void onTouched(Object state, boolean collided)
	{
//		if (state == TouchState.CLICK)
//		{
//			quad.setColor(FloatMath.random(), FloatMath.random(), FloatMath.random());
//		}
	}
}

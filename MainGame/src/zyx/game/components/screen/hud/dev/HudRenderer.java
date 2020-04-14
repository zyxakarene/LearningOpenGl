package zyx.game.components.screen.hud.dev;

import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.list.ItemRenderer;
import zyx.engine.touch.TouchData;
import zyx.engine.touch.TouchState;
import zyx.game.controls.input.MouseData;
import zyx.utils.FloatMath;

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

	@Override
	public void onTouched(TouchState state, boolean collided, TouchData data)
	{
		if (state == TouchState.CLICK)
		{
			quad.setColor(FloatMath.random(), FloatMath.random(), FloatMath.random());
		}
	}
}

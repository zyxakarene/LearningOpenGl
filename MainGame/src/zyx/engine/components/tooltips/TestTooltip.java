package zyx.engine.components.tooltips;

import zyx.engine.components.world.WorldObject;

public class TestTooltip extends AbstractTooltip
{

	public TestTooltip(WorldObject target)
	{
		super(target);
		
		minScale = 0.1f;
		hideAtMinScale = true;
	}

	@Override
	public String getResource()
	{
		return "json.tooltip";
	}

}

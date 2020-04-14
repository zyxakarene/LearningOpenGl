package zyx.game.components.screen.radial;

import zyx.engine.components.world.WorldObject;
import zyx.game.components.screen.json.JsonSprite;

public class RadialMenu extends JsonSprite
{

	@Override
	public String getResource()
	{
		return "json.interaction.radial_menu";
	}

	public void showFor(WorldObject worldObject)
	{
		visible = !visible;
	}
}

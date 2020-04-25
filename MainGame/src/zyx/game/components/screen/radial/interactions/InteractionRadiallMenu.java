package zyx.game.components.screen.radial.interactions;

import zyx.game.components.screen.radial.RadialButtonClickAdaptor;
import zyx.game.components.screen.radial.RadialMenu;
import zyx.game.components.world.interactable.InteractionAction;

public class InteractionRadiallMenu extends RadialMenu
{

	@Override
	protected InteractionAction[] getAllPossibilities()
	{
		return InteractionAction.values();
	}

	@Override
	protected RadialButtonClickAdaptor getClickAdaptor()
	{
		return new InteractionRadialButtonClickAdaptor();
	}

}

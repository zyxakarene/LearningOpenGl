package zyx.game.scene.dev.showcase;

import zyx.game.components.screen.hud.BaseHud;

public class DeferredScene extends CubemapScene
{

	@Override
	protected BaseHud createHud()
	{
		return new BaseHud(true);
	}

}

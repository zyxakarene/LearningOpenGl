package zyx.game.scene.menu;

import zyx.engine.scene.Scene;
import zyx.engine.scene.loading.WaitingProcess;
import zyx.game.components.screen.hud.BaseHud;
import zyx.game.components.screen.hud.MenuHud;

public class MenuScene extends Scene
{

	@Override
	protected void onInitialize()
	{
		addLoadingScreenProcess(new WaitingProcess(15, "Pretending to load"));
	}

	@Override
	protected BaseHud createHud()
	{
		return new MenuHud();
	}

}

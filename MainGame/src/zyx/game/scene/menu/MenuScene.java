package zyx.game.scene.menu;

import zyx.engine.scene.Scene;
import zyx.game.components.screen.hud.BaseHud;
import zyx.game.components.screen.hud.MenuHud;

public class MenuScene extends Scene
{

	@Override
	protected BaseHud createHud()
	{
		return new MenuHud();
	}

}

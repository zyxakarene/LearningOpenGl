package zyx.game.controls;

import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.RayPicker;
import zyx.game.controls.input.InputManager;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.sound.SoundManager;

public class MegaManager
{

	public static void update(long timestamp, int elapsed)
	{
		ResourceLoader.getInstance().handleResourceReplies();

		InputManager.getInstance().update(timestamp, elapsed);
		ClickDispatcher.getInstance().dispatchEvents();
		
		SoundManager.getInstance().update(timestamp, elapsed);

		RayPicker.getInstance().updateMousePos(MouseData.instance.x, MouseData.instance.y);
	}
}

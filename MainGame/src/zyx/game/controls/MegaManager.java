package zyx.game.controls;

import zyx.engine.components.animations.MeshAnimator;
import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.game.controls.input.InputManager;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.sound.SoundManager;
import zyx.net.io.ConnectionLoader;

public class MegaManager
{

	public static void update(long timestamp, int elapsed)
	{
		ResourceLoader.getInstance().handleResourceReplies();
		ConnectionLoader.getInstance().handleConnectionResponses();

		MeshAnimator.getInstance().update(timestamp, elapsed);
		InputManager.getInstance().update(timestamp, elapsed);
		ClickDispatcher.getInstance().dispatchEvents();
		
		SoundManager.getInstance().update(timestamp, elapsed);

		RayPicker.getInstance().updateMousePos(MouseData.data.x, MouseData.data.y);
	}
}

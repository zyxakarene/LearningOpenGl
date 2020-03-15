package zyx.game.controls;

import zyx.engine.components.animations.MeshAnimator;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.meshbatch.MeshBatchManager;
import zyx.engine.components.tooltips.TooltipManager;
import zyx.engine.touch.MouseTouchManager;
import zyx.engine.utils.ClickDispatcher;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.game.components.screen.json.JsonSpriteAnimator;
import zyx.game.controls.input.InputManager;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.sound.SoundManager;
import zyx.game.network.PingManager;
import zyx.net.core.ConnectionHandler;
import zyx.opengl.particles.ParticleManager;
import zyx.utils.tasks.TaskScheduler;
import zyx.utils.tween.TweenManager;

public class MegaManager
{

	public static void update(long timestamp, int elapsed)
	{
		TaskScheduler.getInstance().handleReplies();
		ResourceLoader.getInstance().handleReplies();
		ConnectionHandler.getInstance().handleReplies();

		ParticleManager.getInstance().update(timestamp, elapsed);
		TooltipManager.getInstance().update(timestamp, elapsed);
		CubemapManager.getInstance().update(timestamp, elapsed);
		MeshAnimator.getInstance().update(timestamp, elapsed);
		JsonSpriteAnimator.getInstance().update(timestamp, elapsed);
		InputManager.getInstance().update(timestamp, elapsed);
		MouseTouchManager.getInstance().update(timestamp, elapsed);
		ClickDispatcher.getInstance().dispatchEvents();
		PingManager.getInstance().update(timestamp, elapsed);
		MeshBatchManager.getInstance().update(timestamp, elapsed);
		TweenManager.getInstance().update(timestamp, elapsed);

		SoundManager.getInstance().update(timestamp, elapsed);

		RayPicker.getInstance().updateMousePos(MouseData.data.x, MouseData.data.y);
	}
}

package zyx.game.scene.dev.showcase;

import zyx.engine.scene.loading.WaitingProcess;
import zyx.game.scene.dev.DebugScene;

public class LoadingScene extends DebugScene
{

	@Override
	protected void onInitialize()
	{
		addPlayerControls();

		addLoadingScreenProcess(new WaitingProcess(200, "Initializing Nebula"));
		addLoadingScreenProcess(new WaitingProcess(200, "Booting up Unity"));
		addLoadingScreenProcess(new WaitingProcess(200, "Building Assets"));
		addLoadingScreenProcess(new WaitingProcess(200, "Other witty loading text"));
	}
}

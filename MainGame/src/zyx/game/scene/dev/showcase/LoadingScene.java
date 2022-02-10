package zyx.game.scene.dev.showcase;

import zyx.engine.scene.loading.WaitingProcess;
import zyx.game.scene.dev.DebugScene;

public class LoadingScene extends DebugScene
{

	@Override
	protected void onInitialize()
	{
		addPlayerControls();

		addLoadingScreenProcess(new WaitingProcess(200, "Refilling Spray Can"));
		addLoadingScreenProcess(new WaitingProcess(200, "Painting Trains"));
		addLoadingScreenProcess(new WaitingProcess(200, "Alerting the Guard"));
		addLoadingScreenProcess(new WaitingProcess(200, "Achieving Highscore"));
	}
}

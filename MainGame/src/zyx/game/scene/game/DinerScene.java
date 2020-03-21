package zyx.game.scene.game;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.scene.loading.WaitingProcess;
import zyx.game.controls.process.impl.AuthenticateLoadingProcess;
import zyx.game.vo.Gender;

public class DinerScene extends GameScene
{

	@Override
	protected void onPreloadResources()
	{
		preloadResource("skybox.texture.desert");
		preloadResource("cubemap.dragon");
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		addLoadingScreenProcess(new AuthenticateLoadingProcess("Zyx", Gender.random()));
		addLoadingScreenProcess(new WaitingProcess(3, "Reticulating Splines"));
		addLoadingScreenProcess(new WaitingProcess(5, "Branching Family Trees"));
		addLoadingScreenProcess(new WaitingProcess(7, "Blurring Reality Lines"));

		world.loadSkybox("skybox.texture.desert");
		CubemapManager.getInstance().load("cubemap.dragon");

		world.setSunRotation(new Vector3f(-33, -5, -21));
	}
}

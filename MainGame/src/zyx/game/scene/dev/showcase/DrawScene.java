package zyx.game.scene.dev.showcase;

import zyx.engine.components.cubemaps.CubemapManager;
import zyx.game.scene.game.GameScene;
import zyx.net.io.controllers.BaseNetworkController;

public class DrawScene extends GameScene
{

	@Override
	protected void onInitializeGameScene()
	{
		world.loadSkybox("skybox.texture.desert");
		CubemapManager.getInstance().load("cubemap.dragon");

		createPlayerObject();
	}

	@Override
	protected BaseNetworkController createNetworkDispatcher()
	{
		return new BaseNetworkController();
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);
	
		if (player != null)
		{
			player.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		if (player != null)
		{
			player.dispose();
			player = null;
		}
	}
	
	
	
	

}

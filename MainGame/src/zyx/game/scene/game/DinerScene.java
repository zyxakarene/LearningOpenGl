package zyx.game.scene.game;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.scene.loading.WaitingProcess;
import zyx.game.components.screen.hud.BaseHud;
import zyx.game.components.screen.hud.DinerHud;
import zyx.game.components.world.IInteractable;
import zyx.game.controls.process.impl.AuthenticateLoadingProcess;
import zyx.game.vo.Gender;

public class DinerScene extends GameScene
{
	private static final boolean ONLINE = true;
	
	private DinerHud dinerHud;
	
	@Override
	protected void onPreloadResources()
	{
		preloadResource("mesh.player");
		preloadResource("skybox.texture.desert");
		preloadResource("cubemap.dragon");
	}

	@Override
	protected void onInitializeGameScene()
	{
		if (ONLINE)
		{
			addLoadingScreenProcess(new AuthenticateLoadingProcess("Zyx", Gender.random()));
		}
		else
		{
			createPlayerObject();
		}

		addLoadingScreenProcess(new WaitingProcess(3, "Reticulating Splines"));
		addLoadingScreenProcess(new WaitingProcess(5, "Branching Family Trees"));
		addLoadingScreenProcess(new WaitingProcess(7, "Blurring Reality Lines"));

		world.loadSkybox("skybox.texture.desert");
		CubemapManager.getInstance().load("cubemap.dragon");

		world.setSunRotation(new Vector3f(-33, -5, -21));
		
		dinerHud = (DinerHud) hud;
	}

	@Override
	protected BaseHud createHud()
	{
		return new DinerHud();
	}

	public void addInteractableObject(IInteractable item)
	{
		addPickedObject(item.getInteractionPhysbox(), dinerHud);
	}

	public void removeInteractableObject(IInteractable item)
	{
		removePickedObject(item.getInteractionPhysbox(), dinerHud);
	}
}

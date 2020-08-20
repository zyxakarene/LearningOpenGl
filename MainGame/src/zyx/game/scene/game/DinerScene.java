package zyx.game.scene.game;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.world.WorldObject;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.scene.loading.WaitingProcess;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.engine.utils.worldpicker.IWorldPickedItem;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.screen.hud.BaseHud;
import zyx.game.components.screen.hud.DinerHud;
import zyx.game.components.world.interactable.IInteractable;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.process.impl.AuthenticateLoadingProcess;
import zyx.game.models.GameModels;
import zyx.game.vo.Gender;

public class DinerScene extends GameScene
{

	private static final boolean ONLINE = true;

	public DinerHud dinerHud;
	public DinerSceneData sceneData;

	private IWorldPickedItem interactionCallback;

	public DinerScene()
	{
		interactionCallback = this::onInteractionCallback;
	}

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
		sceneData = new DinerSceneData(this);

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

	@Override
	public void createPlayerObject()
	{
		super.createPlayerObject();

		sceneData.holderHandler.addItemHolder(player.getUniqueId(), player);
	}

	public void addInteractableObject(IInteractable item)
	{
		addPickedObject(item, GameModels.selection);
		addPickedObject(item, interactionCallback);
	}

	public void removeInteractableObject(IInteractable item)
	{
		removePickedObject(item, GameModels.selection);
		removePickedObject(item, interactionCallback);
	}

	public void onInteractionCallback(ClickedInfo info)
	{
		CursorManager.getInstance().setCursor(GameCursor.HAND);

		if (MouseData.data.isLeftClicked())
		{
			Mouse.setGrabbed(false);
			Mouse.setCursorPosition(ScreenSize.width / 2, ScreenSize.height / 2);

			WorldObject worldObject = info.worldObject;
			if (worldObject instanceof IInteractable)
			{
				IInteractable target = ((IInteractable) worldObject);
				ArrayList<InteractionAction> availibleInteractions = target.getInteractions();

				dinerHud.showInteractions(availibleInteractions);
			}
		}
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);

		if (sceneData != null)
		{
			sceneData.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();

		if (sceneData != null)
		{
			sceneData.dispose();
			sceneData = null;
		}
	}

}

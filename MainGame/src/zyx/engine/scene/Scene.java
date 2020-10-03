package zyx.engine.scene;

import zyx.engine.components.screen.base.Stage;
import zyx.engine.components.world.World3D;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.scene.loading.ILoadingScreenDone;
import zyx.engine.scene.loading.LoadingScreenProcess;
import zyx.engine.scene.loading.LoadingScreenProcessQueue;
import zyx.engine.scene.preloading.ResourcePreloadProcess;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.game.components.screen.debug.DebugPanel;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.MegaManager;
import zyx.opengl.GLUtils;
import zyx.game.components.screen.hud.BaseHud;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.lights.LightsManager;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.opengl.camera.Camera;
import zyx.opengl.particles.ParticleManager;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.cheats.DebugContainer;

public class Scene implements ILoadingScreenDone
{

	protected static Scene current;

	protected DebugContainer debugContainer;

	protected Stage stage;
	protected World3D world;
	protected CameraController camera;

	protected BaseHud hud;
	protected LoadingScreenProcessQueue loadingQueue;

	private boolean ready;
	
	private DebugPanel debugPanel;

	public Scene()
	{
		world = World3D.getInstance();
		stage = Stage.getInstance();
		camera = new CameraController();

		debugContainer = DebugContainer.getInstance();
		loadingQueue = new LoadingScreenProcessQueue();

		ready = false;
		current = this;
	}

	public static Scene getCurrent()
	{
		return current;
	}

	protected void enablePing()
	{

	}

	final void initialize()
	{
//		debugPanel = new DebugPanel();
		
		world.addChild(debugContainer);
		hud = createHud();
		stage.hudLayer.addChild(hud);
//		stage.hudLayer.addChild(debugPanel);

		onPreloadResources();

		onInitialize();
	}

	@Override
	public final void onLoadingScreenCompleted()
	{
		ready = true;
	}

	protected void onPreloadResources()
	{
	}

	protected final void preloadResource(String resource)
	{
		loadingQueue.addProcess(new ResourcePreloadProcess(resource));
	}

	protected final void addLoadingScreenProcess(LoadingScreenProcess process)
	{
		loadingQueue.addProcess(process);
	}

	final void update(long timestamp, int elapsedTime)
	{
		GLUtils.errorCheck();

		CursorManager.getInstance().setCursor(GameCursor.POINTER);
		stage.checkStageMouseInteractions(MouseData.data.x, MouseData.data.y);

		debugContainer.update(timestamp, elapsedTime);

		camera.update(timestamp, elapsedTime);

		RayPicker.getInstance().updateMousePos(MouseData.data.x, MouseData.data.y);

		MegaManager.update(timestamp, elapsedTime);

		if (ready)
		{
			onUpdate(timestamp, elapsedTime);
			stage.update(timestamp, elapsedTime);
		}

		CursorManager.getInstance().update();

		GLUtils.errorCheck();
	}

	final void draw()
	{
		if (ready)
		{
			SharedShaderObjects.combineMatrices();
			Camera.getInstance().setViewFrustum(SharedShaderObjects.WORLD_PROJECTION_VIEW_TRANSFORM);

			ShaderManager.getInstance().draw();
			
			LightsManager.getInstane().uploadLights();
			world.drawScene();

			onDraw();
		}

		stage.drawStage();

		GLUtils.errorCheck();
	}

	protected void onUpdate(long timestamp, int elapsedTime)
	{
	}

	protected BaseHud createHud()
	{
		return new BaseHud(true);
	}

	protected BaseNetworkController createNetworkDispatcher()
	{
		return new BaseNetworkController();
	}

	protected void onDraw()
	{
	}

	protected void onDispose()
	{
	}

	protected void onInitialize()
	{
	}

	final void dispose()
	{
		onDispose();

		camera.dispose();

		ParticleManager.getInstance().clear();
		Camera.getInstance().clearViewObject();

		if (debugPanel != null)
		{
			debugPanel.removeFromParent(true);
			debugPanel = null;
		}
		
		if (hud != null)
		{
			hud.dispose();
			hud = null;
		}

		if (loadingQueue != null)
		{
			loadingQueue.dispose();
			loadingQueue = null;
		}

		if (world != null)
		{
			world.removeSkybox();
		}

		stage = null;
		world = null;
		camera = null;
		current = null;
	}

	boolean isReady()
	{
		return ready;
	}

	LoadingScreenProcessQueue getLoadingScreenProcess()
	{
		return loadingQueue;
	}
}

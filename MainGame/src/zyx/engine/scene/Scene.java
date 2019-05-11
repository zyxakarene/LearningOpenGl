package zyx.engine.scene;

import zyx.engine.components.screen.base.Stage;
import zyx.engine.components.world.World3D;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.scene.preloading.ResourcePreloadProcess;
import zyx.engine.utils.callbacks.ICallback;
import zyx.engine.utils.worldpicker.WorldPicker;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.MegaManager;
import zyx.opengl.GLUtils;
import zyx.utils.interfaces.IPhysbox;
import zyx.engine.utils.worldpicker.IHoveredItem;
import zyx.game.components.screen.debug.DebugPanel;
import zyx.game.components.screen.hud.BaseHud;
import zyx.game.components.screen.hud.MainHud;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.lights.LightsManager;
import zyx.game.controls.process.ProcessQueue;
import zyx.opengl.camera.Camera;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.cheats.DebugContainer;

public class Scene
{

	private WorldPicker picker;
	protected DebugContainer debugContainer;
	
	protected Stage stage;
	protected World3D world;
	protected CameraController camera;
	
	protected BaseHud hud;

	private ProcessQueue preloadQueue;
	private boolean ready;
	
	public DebugPanel debugPanel;
	
	public Scene()
	{
		picker = new WorldPicker();
		
		world = World3D.instance;
		stage = Stage.instance;
		camera = new CameraController();
		
		debugContainer = DebugContainer.getInstance();
		preloadQueue = new ProcessQueue();
		
		ready = false;
	}

	protected void addPickedObject(IPhysbox object, IHoveredItem clickCallback)
	{
		picker.addObject(object, clickCallback);
	}

	protected void removePickedObject(IPhysbox object, IHoveredItem clickCallback)
	{
		picker.removeObject(object, clickCallback);
	}
	
	final void initialize()
	{
		world.addChild(debugContainer);
		hud = createHud();
		if (hud != null)
		{
			stage.addChild(hud);
		}
		
//		debugPanel = new DebugPanel();
//		stage.addChild(debugPanel);
		
		onPreloadResources();
		
		ICallback<ProcessQueue> onCompleted = (ProcessQueue data) ->
		{
			onInitialize();
			ready = true;
		};
		
		preloadQueue.start(onCompleted);
	}

	protected void onPreloadResources()
	{
	}
	
	protected final void preloadResource(String resource)
	{
		preloadQueue.addProcess(new ResourcePreloadProcess(resource));
	}
	
	final void update(long timestamp, int elapsedTime)
	{
		GLUtils.errorCheck();
		
		CursorManager.getInstance().setCursor(GameCursor.POINTER);
		stage.checkStageMouseInteractions(MouseData.data.x, MouseData.data.y);
		
		debugContainer.update(timestamp, elapsedTime);
		picker.update();

		if (hud != null)
		{
			hud.update(timestamp, elapsedTime);
		}
		
		MegaManager.update(timestamp, elapsedTime);
		camera.update(timestamp, elapsedTime);
		
		if (ready)
		{
			onUpdate(timestamp, elapsedTime);
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
			
			LightsManager.getInstane().uploadLights();
			world.drawScene();

			onDraw();

			GLUtils.disableDepthTest();
			GLUtils.disableCulling();
			stage.drawStage();
			GLUtils.enableCulling();
			GLUtils.enableDepthTest();

			GLUtils.errorCheck();
		}
	}

	protected void onUpdate(long timestamp, int elapsedTime)
	{
	}
	
	protected BaseHud createHud()
	{
		return new BaseHud();
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
		camera.dispose();
		picker.dispose();
		
		onDispose();

		if (hud != null)
		{
			hud.dispose();
			hud = null;
		}
		
		if (preloadQueue != null)
		{
			preloadQueue.dispose();
			preloadQueue = null;
		}
		
		if(debugPanel != null)
		{
			debugPanel.dispose();
			debugPanel = null;
		}
		
		stage = null;
		world = null;
		camera = null;
		picker = null;
	}

	boolean isReady()
	{
		return ready;
	}
}

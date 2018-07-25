package zyx.engine.scene;

import zyx.engine.components.screen.Stage;
import zyx.engine.components.world.World3D;
import zyx.engine.curser.CursorManager;
import zyx.engine.utils.worldpicker.WorldPicker;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.MegaManager;
import zyx.opengl.GLUtils;
import zyx.utils.interfaces.IPhysbox;
import zyx.engine.utils.worldpicker.IHoveredItem;
import zyx.utils.cheats.DebugContainer;

public class Scene
{

	private WorldPicker picker;
	protected DebugContainer debugContainer;
	
	protected Stage stage;
	protected World3D world;
	protected CameraController camera;

	public Scene()
	{
		picker = new WorldPicker();
		
		world = World3D.instance;
		stage = Stage.instance;
		camera = new CameraController();
		
		debugContainer = DebugContainer.getInstance();
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
		
		onInitialize();
	}

	final void update(long timestamp, int elapsedTime)
	{
		GLUtils.errorCheck();
		
		stage.checkStageMouseInteractions();
		
		debugContainer.update(timestamp, elapsedTime);
		picker.update();

		MegaManager.update(timestamp, elapsedTime);
		camera.update(timestamp, elapsedTime);
		onUpdate(timestamp, elapsedTime);

		CursorManager.getInstance().update();

		GLUtils.errorCheck();
	}

	final void draw()
	{
		world.drawScene();

		onDraw();

		GLUtils.disableDepthTest();
		GLUtils.disableCulling();
		stage.drawStage();
		GLUtils.enableCulling();
		GLUtils.enableDepthTest();

		GLUtils.errorCheck();
	}

	protected void onUpdate(long timestamp, int elapsedTime)
	{
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

		onDispose();

		stage = null;
		world = null;
		camera = null;
	}
}

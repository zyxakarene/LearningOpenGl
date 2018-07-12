package zyx.engine.scene;

import zyx.engine.components.screen.Stage;
import zyx.engine.components.world.World3D;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.MegaManager;
import zyx.opengl.GLUtils;

public class Scene
{

	protected Stage stage;
	protected World3D world;
	protected CameraController camera;

	public Scene()
	{
		world = World3D.instance;
		stage = Stage.instance;
		camera = new CameraController();
	}
	
	final void initialize()
	{
		onInitialize();
	}
	
	final void update(long timestamp, int elapsedTime)
	{
		GLUtils.errorCheck();
		
		CursorManager.getInstance().setCursor(GameCursor.POINTER);
		
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

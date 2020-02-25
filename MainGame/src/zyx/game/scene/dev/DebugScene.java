package zyx.game.scene.dev;

import java.util.ArrayList;
import zyx.engine.components.world.WorldObject;
import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.FreeFlyBehavior;
import zyx.game.components.world.player.PlayerObject;
import zyx.game.scene.game.GameScene;
import zyx.opengl.camera.Camera;
import zyx.utils.interfaces.IUpdateable;

public class DebugScene extends GameScene
{

	protected ArrayList<WorldObject> objects;

	public DebugScene()
	{
		objects = new ArrayList<>();
	}

	protected void addPlayerControls()
	{
		world.loadSkybox("skybox.texture.desert");

		PlayerObject player = new PlayerObject();
		player.addBehavior(new FreeFlyBehavior());
		player.addBehavior(new CameraUpdateViewBehavior());
		Camera.getInstance().setViewObject(player);
		
		world.addChild(player);
		objects.add(player);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);

		for (WorldObject object : objects)
		{
			if (object instanceof IUpdateable)
			{
				((IUpdateable) object).update(timestamp, elapsedTime);
			}
		}
	}

	@Override
	protected void onDispose()
	{
		for (WorldObject object : objects)
		{
			object.dispose();
		}
		
		super.onDispose();
	}

}

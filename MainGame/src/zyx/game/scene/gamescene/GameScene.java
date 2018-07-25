package zyx.game.scene.gamescene;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import zyx.OnTeaPotClicked;
import zyx.engine.scene.Scene;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.MeshObject;
import zyx.utils.FloatMath;
import zyx.utils.cheats.Print;
import zyx.engine.utils.worldpicker.IHoveredItem;
import zyx.game.components.SimpleMesh;
import zyx.game.controls.input.KeyboardData;
import zyx.utils.cheats.DebugPoint;

public class GameScene extends Scene
{

	private ArrayList<MeshObject> objects;

	public GameScene()
	{
		objects = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 1; i++)
		{
			MeshObject worm = new MeshObject(true);
			worm.load("mesh.worm.worm");
			((AnimatedMesh) worm.mesh).setAnimation("wiggle");

			world.addChild(worm);
			objects.add(worm);

			addPickedObject(worm, new OnTeaPotClicked());
		}
	}

	private SimpleMesh box;
	private SimpleMesh platform;

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (box != null && KeyboardData.data.wasPressed(Keyboard.KEY_T))
		{
			box.dispose();
			box = null;
			
			platform.dispose();
			platform = null;
		}

		if (box == null && KeyboardData.data.wasPressed(Keyboard.KEY_R))
		{
			box = new SimpleMesh();
			box.load("mesh.box");
			
			platform = new SimpleMesh();
			platform.load("mesh.platform");
			
			world.addChild(box);
			world.addChild(platform);
		}

		for (MeshObject object : objects)
		{
			object.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		for (MeshObject object : objects)
		{
			object.dispose();
		}

		objects.clear();
		objects = null;
	}
}

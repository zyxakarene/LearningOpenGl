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
			MeshObject knight = new MeshObject(true);
			knight.load("mesh.worm.worm");
			((AnimatedMesh) knight.mesh).setAnimation("wiggle");
			knight.setX(FloatMath.random() * 300);
			knight.setY(FloatMath.random() * 300);

			world.addChild(knight);
			objects.add(knight);

			addPickedObject(knight, new OnTeaPotClicked());
		}
	}

	private SimpleMesh point;

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (point != null && !point.hasParent())
		{
			point.dispose();
			point = null;
		}
		
		if (point != null && KeyboardData.data.wasPressed(Keyboard.KEY_T))
		{
			world.removeChild(point);
		}

		if (point == null && KeyboardData.data.wasPressed(Keyboard.KEY_R))
		{
			point = new SimpleMesh();
			point.load("mesh.box");
			world.addChild(point);
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

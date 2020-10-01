package zyx.game.scene.dev;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.OnTeaPotClicked;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.FoodItem;
import zyx.game.controls.input.KeyboardData;
import zyx.game.vo.DishType;
import zyx.opengl.models.implementations.shapes.Box;

public class TestScene extends DebugScene
{

	private AnimatedMesh mesh;
	private FoodItem box;

	public TestScene()
	{
	}

	@Override
	protected void onPreloadResources()
	{
	}

	@Override
	protected void onInitialize()
	{
		addPlayerControls();

		mesh = new AnimatedMesh();
		mesh.load("mesh.character");
		world.addChild(mesh);
		mesh.setAnimation("idleCarry");
		
		
		box = new FoodItem(DishType.STEAK);
		box.load();
		mesh.addChildAsAttachment(box, "bone_carry");
		
		picker.addObject(mesh, new OnTeaPotClicked());
		
		objects.add(mesh);
		objects.add(box);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);

		Vector3f pos = mesh.getPosition(true, null);
		
		if (KeyboardData.data.isDown(Keyboard.KEY_RIGHT))
		{
			pos.x += 1f;
		}
		else if	(KeyboardData.data.isDown(Keyboard.KEY_LEFT))
		{
			pos.x -= 1f;
		}
		
		if (KeyboardData.data.isDown(Keyboard.KEY_UP))
		{
			pos.z += 1f;
		}
		else if	(KeyboardData.data.isDown(Keyboard.KEY_DOWN))
		{
			pos.z -= 1f;
		}
		
		mesh.setPosition(true, pos);
	}
}

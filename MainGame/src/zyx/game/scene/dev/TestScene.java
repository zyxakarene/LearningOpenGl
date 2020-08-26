package zyx.game.scene.dev;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.OnTeaPotClicked;
import zyx.game.components.SimpleMesh;
import zyx.game.controls.input.KeyboardData;

public class TestScene extends DebugScene
{

	private SimpleMesh teapot;

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

		teapot = new SimpleMesh();
		teapot.load("mesh.player");
		objects.add(teapot);
		world.addChild(teapot);
		
		picker.addObject(teapot, new OnTeaPotClicked());
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);

		Vector3f pos = teapot.getPosition(true, null);
		
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
		
		teapot.setPosition(true, pos);
	}
}

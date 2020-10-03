package zyx.game.scene.dev.showcase;

import org.lwjgl.input.Keyboard;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;
import zyx.game.controls.input.KeyboardData;
import zyx.game.scene.dev.DebugScene;

public class SkeletonScene extends DebugScene
{

	private AnimatedMesh mesh;
	private boolean idle;

	public SkeletonScene()
	{
		idle = true;
	}

	@Override
	protected void onInitialize()
	{
		addPlayerControls();

		mesh = new AnimatedMesh();
		mesh.load("mesh.character");
		mesh.setAnimation("idle");
		world.addChild(mesh);
		objects.add(mesh);
		
		SimpleMesh platform = new SimpleMesh();
		platform.load("mesh.platform");
		world.addChild(platform);
		objects.add(platform);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			idle = !idle;
			if (idle)
			{
				mesh.setAnimation("idle");
			}
			else
			{
				mesh.setAnimation("walk");
			}
		}
	}
	
	

}

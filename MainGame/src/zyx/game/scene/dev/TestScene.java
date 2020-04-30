package zyx.game.scene.dev;

import org.lwjgl.input.Keyboard;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.MeshObject;
import zyx.game.controls.input.KeyboardData;
import zyx.utils.FloatMath;

public class TestScene extends DebugScene
{

	public TestScene()
	{
	}

	@Override
	protected void onPreloadResources()
	{
	}

	@Override
	protected void onInitializeGameScene()
	{
		addPlayerControls();
		
		AnimatedMesh jasper = new AnimatedMesh();
		jasper.load("mesh.jasper");
		jasper.setAnimation("walking");
		objects.add(jasper);
		world.addChild(jasper);
	}

//	@Override
//	protected MainHud createHud()
//	{
//		return new MainHud();
//	}
	
	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			AnimatedMesh tempKnight = new AnimatedMesh();
			tempKnight.setX((FloatMath.random() * 400) - 200);
			tempKnight.setY((FloatMath.random() * 400) - 200);
			tempKnight.setRotZ(FloatMath.random() * 360);
			
			tempKnight.load("mesh.jasper");
			
			if (Math.random() > 0.5)
			{
				tempKnight.setAnimation("walking");
			}
			else
			{
				tempKnight.setAnimation("action");	
			}
			
			objects.add(tempKnight);
			world.addChild(tempKnight);
		}
	}
}

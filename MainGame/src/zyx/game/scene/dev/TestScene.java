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
		preloadResource("flat_bg");
	}

	@Override
	protected void onInitializeGameScene()
	{
		addPlayerControls();
		
		for (int i = 0; i < 0; i++)
		{
			MeshObject model = new MeshObject();
			model.load("mesh.box");
			model.setX(-20);
//			model.setY(FloatMath.random() * 300);

			world.addChild(model);
			objects.add(model);
		}

		AnimatedMesh knight = new AnimatedMesh();
		knight.load("mesh.knight.knight");
		knight.setAnimation("attack");
		objects.add(knight);
		world.addChild(knight);
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
		
		if (KeyboardData.data.isDown(Keyboard.KEY_SPACE))
		{
			AnimatedMesh tempKnight = new AnimatedMesh();
			tempKnight.setX((FloatMath.random() * 200) - 100);
			tempKnight.setY((FloatMath.random() * 200) - 100);
			tempKnight.setRotZ(FloatMath.random() * 360);
			
			tempKnight.load("mesh.knight.knight");
			
			if (Math.random() > 0.5)
			{
				tempKnight.setAnimation("attack");
			}
			else
			{
				tempKnight.setAnimation("walk");	
			}
			
			objects.add(tempKnight);
			world.addChild(tempKnight);
		}
	}
}

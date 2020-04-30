package zyx.game.scene.dev;

import org.lwjgl.input.Keyboard;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;
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
		
		AnimatedMesh box = new AnimatedMesh();
		box.load("mesh.furniture.fridge");
		objects.add(box);
		world.addChild(box);
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
			AnimatedMesh tempJasper = new AnimatedMesh();
			tempJasper.setX((FloatMath.random() * 400) - 200);
			tempJasper.setY((FloatMath.random() * 400) - 200);
			tempJasper.setRotZ(FloatMath.random() * 360);
			
			tempJasper.load("mesh.jasper");
			
			if (Math.random() > 0.5)
			{
				tempJasper.setAnimation("walking");
			}
			else
			{
				tempJasper.setAnimation("action");	
			}
			
			objects.add(tempJasper);
			world.addChild(tempJasper);
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			SimpleMesh tempBox = new SimpleMesh();
			tempBox.setX((FloatMath.random() * 400) - 200);
			tempBox.setY((FloatMath.random() * 400) - 200);
			tempBox.setRotZ(FloatMath.random() * 360);
			
			tempBox.load("mesh.box");
			
			objects.add(tempBox);
			world.addChild(tempBox);
		}
	}
}

package zyx.game.scene.dev;

import org.lwjgl.input.Keyboard;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;
import zyx.game.controls.input.KeyboardData;
import zyx.utils.FloatMath;

public class TestScene extends DebugScene
{
	private AnimatedMesh jasper;
	private AnimatedMesh jasperNoBlend;
	private boolean walking;
	
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
		
		jasperNoBlend = new AnimatedMesh();
		jasperNoBlend.load("mesh.jasper");
		jasperNoBlend.setAnimation("walking");
		jasperNoBlend.setX(50);
		
		jasper = new AnimatedMesh();
		jasper.load("mesh.jasper");
		jasper.setAnimation("walking");
		walking = true;
		
		objects.add(jasperNoBlend);
		world.addChild(jasperNoBlend);
		
		objects.add(jasper);
		world.addChild(jasper);
		
//		AnimatedMesh box = new AnimatedMesh();
//		box.load("mesh.furniture.fridge");
//		box.setAnimation("open");
//		box.lookAt(100, 0, 0);
//		objects.add(box);
//		world.addChild(box);
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
			walking = !walking;
			if (walking)
			{
				jasper.setAnimation("walking");
				jasperNoBlend.setAnimation("walking");
			}
			else
			{
				jasper.setAnimation("action");
				jasperNoBlend.setAnimation("action");
			}
			jasperNoBlend.clearBlend();
			
//			AnimatedMesh tempJasper = new AnimatedMesh();
//			tempJasper.setX((FloatMath.random() * 400) - 200);
//			tempJasper.setY((FloatMath.random() * 400) - 200);
//			tempJasper.setRotZ(FloatMath.random() * 360);
//			
//			tempJasper.load("mesh.jasper");
//			
//			if (Math.random() > 0.5)
//			{
//				tempJasper.setAnimation("walking");
//			}
//			else
//			{
//				tempJasper.setAnimation("action");	
//			}
//			
//			objects.add(tempJasper);
//			world.addChild(tempJasper);
		}
		
//		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
//		{
//			AnimatedMesh tempBox = new AnimatedMesh();
//			tempBox.setX((FloatMath.random() * 400) - 200);
//			tempBox.setY((FloatMath.random() * 400) - 200);
//			tempBox.setRotZ(FloatMath.random() * 360);
//			
//			tempBox.load("mesh.furniture.fridge");
//			tempBox.setAnimation("open");
//			tempBox.lookAt(0, 0, 0);
//			
//			objects.add(tempBox);
//			world.addChild(tempBox);
//		}
	}
}

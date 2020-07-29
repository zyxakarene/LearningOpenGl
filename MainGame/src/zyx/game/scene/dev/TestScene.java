package zyx.game.scene.dev;

import org.lwjgl.input.Keyboard;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.furniture.Floor;
import zyx.game.components.world.furniture.FurnitureSetupVo;
import zyx.game.controls.input.KeyboardData;
import zyx.game.joining.data.FurnitureSetupData;
import zyx.utils.FloatMath;

public class TestScene extends DebugScene
{

	AnimatedMesh jasper1;
	AnimatedMesh jasper2;

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

		jasper1 = new AnimatedMesh();
		jasper1.debugging = true;
		jasper1.load("mesh.jasper");
		jasper1.setAnimation("walking");
		objects.add(jasper1);
		world.addChild(jasper1);

		jasper2 = new AnimatedMesh();
		jasper2.load("mesh.jasper");
		jasper2.setAnimation("walking");
		jasper2.setX(50);
		objects.add(jasper2);
		world.addChild(jasper2);
		
		Floor floor = new Floor();
		FurnitureSetupVo setup = new FurnitureSetupVo();
		setup.fromData(FurnitureSetupData.INSTANCE, 0);
		floor.load(setup);
		world.addChild(floor);
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
//			AnimatedMesh tempJasper = new AnimatedMesh();
//			tempJasper.setX((FloatMath.random() * 400) - 200);
//			tempJasper.setY((FloatMath.random() * 400) - 200);
//			tempJasper.setRotZ(FloatMath.random() * 360);
//
//			tempJasper.load("mesh.jasper");

			if (Math.random() > 0.5)
			{
				jasper2.setAnimation("walking");
			}
			else
			{
				jasper2.setAnimation("action");
			}

//			objects.add(tempJasper);
//			world.addChild(tempJasper);
		}
	}
}

package zyx.game.scene.dev;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.game.components.AnimatedMesh;
import zyx.game.controls.input.KeyboardData;
import zyx.utils.FloatMath;

public class TestScene extends DebugScene
{
	private AnimatedMesh jasperXPos;
	private AnimatedMesh jasperXNeg;
	private AnimatedMesh jasperYPos;
	private AnimatedMesh jasperYNeg;
	private boolean walking;
	
	private ArrayList<AnimatedMesh> jaspers;
	
	public TestScene()
	{
		jaspers = new ArrayList<>();
	}

	@Override
	protected void onPreloadResources()
	{
	}

	@Override
	protected void onInitialize()
	{
		addPlayerControls();
		
		jasperXPos = GetJasper(new Vector3f(1, 0, 0));
		jasperXNeg= GetJasper(new Vector3f(-1, 0, 0));
		jasperYPos = GetJasper(new Vector3f(0, 1, 0));
		jasperYNeg= GetJasper(new Vector3f(0, -1, 0));
		
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
			for (AnimatedMesh object : jaspers)
			{
				System.out.println(object.getDir(true, null));
				object.lookAt(camera.getPosition(false, null));
				System.out.println(object.getDir(true, null));
				System.out.println(object.getScale(true, null));
				System.out.println("=");
			}
		}
	}

	private AnimatedMesh GetJasper(Vector3f lookAt)
	{
//		lookAt.x = FloatMath.random();
//		lookAt.y = FloatMath.random();
//		lookAt.normalise();
		
		AnimatedMesh jasper = new AnimatedMesh();
		jasper.load("mesh.player");
//		jasper.setAnimation("walking");
		
		Vector3f pos = new Vector3f();
		pos.x = lookAt.x * -50;
		pos.y = lookAt.y * -50;
		pos.z = lookAt.z * -50;
		jasper.setPosition(true, pos);
		
		lookAt.x = pos.x + (lookAt.x * 100);
		lookAt.y = pos.y + (lookAt.y * 100);
		lookAt.z = pos.z + (lookAt.z * 100);
		jasper.lookAt(lookAt);
		
		objects.add(jasper);
		world.addChild(jasper);
		jaspers.add(jasper);
		
		return jasper;
	}
}

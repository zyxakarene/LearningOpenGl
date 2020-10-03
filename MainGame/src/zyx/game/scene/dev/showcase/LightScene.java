package zyx.game.scene.dev.showcase;

import org.lwjgl.input.Keyboard;
import zyx.engine.components.world.GameLight;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.behavior.misc.RotateBehavior;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;
import zyx.game.controls.input.KeyboardData;
import zyx.game.scene.dev.DebugScene;
import zyx.opengl.GLUtils;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;

public class LightScene extends DebugScene
{

	private Behavior rotater;
	private boolean active;

	@Override
	protected void onInitialize()
	{
		addPlayerControls();

		MeshObject spinner = new MeshObject();
		spinner.addBehavior(new RotateBehavior());
		rotater = spinner.getBehaviorById(BehaviorType.ROTATE);
		spinner.load("mesh.dragon");
		active = true;
		
		world.addChild(spinner);
		objects.add(spinner);
		
		for (int i = 0; i < GameConstants.LIGHT_COUNT; i++)
		{
			GameObject lightContainer = new GameObject();
			GameLight light = new GameLight((int) (0xFFFFFF * Math.random()), 200);

			lightContainer.addChild(light);
			world.addChild(lightContainer);

			float x = -20f + (40f * FloatMath.random());
			float y = -20f + (40f * FloatMath.random());
			float z = (40f * FloatMath.random());
			lightContainer.setPosition(true, x, y, z);
			GLUtils.errorCheck();

			lightContainer.addBehavior(new JiggleBehavior(true));
			objects.add(lightContainer);
		}
	}
	
	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			active = !active;
			rotater.setActive(active);
		}
	}
}

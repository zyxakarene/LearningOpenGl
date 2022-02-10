package zyx.game.scene.dev.showcase;

import org.lwjgl.input.Keyboard;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.misc.RotateBehavior;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;
import zyx.game.components.SimpleMesh;
import zyx.game.controls.input.KeyboardData;
import zyx.game.scene.dev.DebugScene;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.shapes.Sphere;
import zyx.opengl.textures.ColorTexture;

public class CubemapScene extends DebugScene
{

	private Behavior rotater;
	private boolean active;

	@Override
	protected void onInitialize()
	{
		addPlayerControls();

		SimpleMesh failure = new SimpleMesh();
		failure.load("mesh.test.failure");
		failure.setScale(0.5f, 0.5f, 0.5f);
		failure.setPosition(true, 0, 0, -50);
		world.addChild(failure);

		SimpleMesh dragon = new SimpleMesh();
		dragon.load("mesh.dragon");
		dragon.setScale(0.5f, 0.5f, 0.5f);
		world.addChild(dragon);
		
		SimpleMesh platform = new SimpleMesh();
		platform.load("mesh.platform");
		world.addChild(platform);

		GameObject spinner = new GameObject();
		spinner.addBehavior(new RotateBehavior());
		rotater = spinner.getBehaviorById(BehaviorType.ROTATE);
		active = true;

		Sphere sphere1 = new Sphere(5);
		Sphere sphere2 = new Sphere(5);
		Sphere sphere3 = new Sphere(5);
		Sphere sphere4 = new Sphere(5);
		
		sphere1.setPosition(false, -20, -20, 10);
		sphere2.setPosition(false, 20, -20, 10);
		sphere3.setPosition(false, 20, 20, 10);
		sphere4.setPosition(false, -20, 20, 10);

		spinner.addChild(sphere1);
		spinner.addChild(sphere2);
		spinner.addChild(sphere3);
		spinner.addChild(sphere4);
		world.addChild(spinner);

		objects.add(spinner);
		objects.add(dragon);
		objects.add(platform);
		objects.add(failure);
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

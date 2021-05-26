package zyx.game.scene.dev;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.OnTeaPotClicked;
import zyx.engine.components.world.WorldObject;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.FoodItem;
import zyx.game.controls.input.KeyboardData;
import zyx.game.vo.DishType;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.LightingPassRenderer;
import zyx.opengl.materials.Culling;
import zyx.opengl.materials.ZTest;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.shapes.Box;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.GameConstants;

public class TestScene extends DebugScene
{

	private Box floor;
	private Box box;
	private GameObject obj;

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

		floor = new Box(400, 400, 10);
		floor.setZ(-40);
		world.addChild(floor);
		
		
		obj = new GameObject();
		obj.addBehavior(new JiggleBehavior());
		
		box = new Box();
		obj.addChild(box);
		
		obj.setZ(20);
		world.addChild(obj);
		
		objects.add(floor);
		objects.add(box);
		
		box.onLoaded(this::onBoxLoaded);
	}
	
	private void onBoxLoaded(SimpleMesh mesh)
	{
		WorldModelMaterial mat1 = mesh.cloneMaterial(0);
		WorldModelMaterial mat2 = mesh.cloneMaterial(1);
		
		mat1.culling = Culling.NONE;
		mat2.culling = Culling.FRONT;
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);
		
		obj.update(timestamp, elapsedTime);
	}
}

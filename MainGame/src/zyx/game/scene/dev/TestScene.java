package zyx.game.scene.dev;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.OnTeaPotClicked;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.FoodItem;
import zyx.game.controls.input.KeyboardData;
import zyx.game.vo.DishType;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.LightingPassRenderer;
import zyx.opengl.models.implementations.shapes.Box;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.GameConstants;

public class TestScene extends DebugScene
{

	private Box floor;
	private Box box;

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
		world.addChild(floor);
		
		
		box = new Box();
		box.setZ(20);
		world.addChild(box);
		
		objects.add(floor);
		objects.add(box);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);
	}
}

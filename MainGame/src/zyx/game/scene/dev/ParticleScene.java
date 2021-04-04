package zyx.game.scene.dev;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.meshbatch.MeshBatchManager;
import zyx.engine.components.world.WorldObject;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.components.MeshObject;
import zyx.game.components.world.meshbatch.CubeEntity;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.GLUtils;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.LightingPassRenderer;
import zyx.opengl.models.implementations.shapes.Box;
import zyx.opengl.particles.ParticleSystem;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.FloatMath;
import zyx.utils.cheats.DebugPoint;

public class ParticleScene extends DebugScene
{

	private ArrayList<DebugPoint> points;
	private MeshObject model;

	public ParticleScene()
	{
		points = new ArrayList<>();
	}

	@Override
	protected void onPreloadResources()
	{
		preloadResource("sprite_sheet_png");
		preloadResource("sprite_sheet_json");
	}

	private int timer = 0;
	private int index = 0;
	private boolean adding;
	
	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);
		
		timer += elapsedTime;
//		if (timer >= 16)
//		{
//			index++;
//			timer = 0;
//			
//			if (index >= 15)
//			{
//				System.out.println("Switched");
//				adding = !adding;
//				index = 0;
//			}
//			
//			if (adding)
//			{
//				System.out.println("Added");
//				DebugPoint addToScene = DebugPoint.addToScene(0, 0, 0, -1);
//			}
//			else
//			{
//				System.out.println("Removed");
//			}
//		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			addBox();
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_E))
		{
			removeBox();
		}
	}

	private void removeBox()
	{
		if (!points.isEmpty())
		{
			int index = (int) (Math.random() * points.size());
			index = points.size() - 1;
			
			DebugPoint obj = points.remove(index);
			obj.kill();
		}
	}

	private void addBox()
	{
		points.add(DebugPoint.addToScene(0, 0, 0, -1));
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 0; i++)
		{
			CubeEntity cube = new CubeEntity();
			float x = (FloatMath.random() * 200f) - 100f;
			float y = (FloatMath.random() * 200f) - 100f;
			float z = (FloatMath.random() * 200f) - 100f;
			cube.position.set(x, y, z);
			cube.scale = 10f;
			MeshBatchManager.getInstance().addEntity(cube);
		}
		
		model = new MeshObject();
		model.load("mesh.box");
		model.setScale(0.1f, 0.1f, 0.1f);

		objects.add(model);
		ParticleSystem localSystem1 = new ParticleSystem();
		localSystem1.load("particles.particle2");
		localSystem1.setZ(40);
		localSystem1.setX(-20);
		localSystem1.setScale(10, 10, 10);
		model.addChild(localSystem1);

		ParticleSystem worldSystem1 = new ParticleSystem();
		worldSystem1.load("particles.world");
		worldSystem1.setZ(40);
		worldSystem1.setX(20);
		worldSystem1.setScale(10, 10, 10);
		model.addChild(worldSystem1);

		model.addBehavior(new JiggleBehavior());
		addPlayerControls();

		GLUtils.errorCheck();
		
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		MeshBatchManager.getInstance().clean();
	}
	
	
}

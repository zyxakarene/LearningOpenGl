package zyx.game.scene.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.cubemaps.saving.CubemapProcess;
import zyx.engine.components.meshbatch.MeshBatchEntity;
import zyx.engine.components.meshbatch.MeshBatchManager;
import zyx.engine.components.tooltips.TestTooltip;
import zyx.engine.components.tooltips.TooltipManager;
import zyx.engine.components.world.GameLight;
import zyx.engine.scene.loading.WaitingProcess;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.behavior.misc.RotateBehavior;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;
import zyx.game.components.world.meshbatch.CubeEntity;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.process.ProcessQueue;
import zyx.game.controls.process.impl.AuthenticateLoadingProcess;
import zyx.game.vo.Gender;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.shapes.Sphere;
import zyx.utils.FloatMath;
import zyx.utils.math.QuaternionUtils;
import zyx.utils.tween.BaseTween;
import zyx.utils.tween.easing.EasingFunction;
import zyx.utils.tween.impl.positionable.TweenPositionableSingleScale;
import zyx.utils.tween.impl.positionable.TweenPositionableVectorPosition;

public class DragonScene extends GameScene implements ICallback<ProcessQueue>
{

	private boolean cubemapping;
	private ProcessQueue processQueue;
	private GameObject spinner;
	
	private BaseTween tweenPos;
	private BaseTween tweenScale;

	
	public DragonScene()
	{
	}

	public static DragonScene getCurrent()
	{
		if (current instanceof DragonScene)
		{
			return (DragonScene) current;
		}
		
		return null;
	}
	
	@Override
	protected void onPreloadResources()
	{
		preloadResource("mesh.player");
		preloadResource("skybox.texture.desert");
		preloadResource("cubemap.dragon");
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
	
		addLoadingScreenProcess(new AuthenticateLoadingProcess("Zyx", Gender.random()));
		addLoadingScreenProcess(new WaitingProcess(3, "Reticulating Splines"));
		addLoadingScreenProcess(new WaitingProcess(5, "Branching Family Trees"));
		addLoadingScreenProcess(new WaitingProcess(7, "Blurring Reality Lines"));
		
//		createPlayerObject();
		
		world.loadSkybox("skybox.texture.desert");
		CubemapManager.getInstance().load("cubemap.dragon");
		
		MeshObject platform = new MeshObject();
		platform.load("mesh.platform");
		world.addChild(platform);
		addGameObject(platform);

		for (int i = 0; i < 4; i++)
		{
			GameObject lightContainer = new GameObject();
			GameLight light = new GameLight((int) (0xFFFFFF * Math.random()), 100);

			lightContainer.addChild(light);
			world.addChild(lightContainer);

			float x = -20f + (40f * FloatMath.random());
			float y = -20f + (40f * FloatMath.random());
			float z = (25f * FloatMath.random());
			lightContainer.setPosition(true, x, y, z);
			GLUtils.errorCheck();

			lightContainer.addBehavior(new JiggleBehavior());
			addGameObject(lightContainer);
		}

		world.setSunRotation(new Vector3f(-33, -5, -21));

		spinner = new GameObject();
		spinner.addBehavior(new RotateBehavior());

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

		addGameObject(spinner);

		TooltipManager.getInstance().register(new TestTooltip(sphere1));
		TooltipManager.getInstance().register(new TestTooltip(sphere2));
		TooltipManager.getInstance().register(new TestTooltip(sphere3));
		TooltipManager.getInstance().register(new TestTooltip(sphere4));

		for (int i = 0; i < 100; i++)
		{
			MeshBatchEntity entityA = new CubeEntity();
			entityA.position.x = (FloatMath.random() * 200) - 100;
			entityA.position.y = (FloatMath.random() * 200) - 100;
			entityA.position.z = (FloatMath.random() * 200) - 100;
			entityA.scale = 3;

			float x = FloatMath.random() * 6.28319f;
			float y = FloatMath.random() * 6.28319f;
			float z = FloatMath.random() * 6.28319f;
			entityA.rotation = QuaternionUtils.toQuat(new Vector3f(x, y, z), null);

			MeshBatchManager.getInstance().addEntity(entityA);
		}
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_R))
		{
			int width = (int) (64 + (Math.random() * 1920 * 0.75));
			int height = (int) (64 + (Math.random() * 1080 * 0.75));
			ScreenSize.changeScreenSize(width, height);
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_T))
		{
			if (tweenPos != null)
			{
				tweenPos.dispose();
				tweenScale.dispose();
			}
			
			tweenPos = new TweenPositionableVectorPosition()
					.setTweenData(spinner, new Vector3f(0, 0, 0), new Vector3f(0, 100, 0), 2000, EasingFunction.BOUNCE_OUT)
					.start();
			
			tweenScale = new TweenPositionableSingleScale()
					.setTweenData(spinner, 1f, 1f, 500, EasingFunction.LINEAR)
					.start();
		}

		//Vector3f camPos = Camera.getInstance().getPosition(false, null);
		//testDragon.lookAt(camPos.x + 0.01f, camPos.y, camPos.z);
		if (!cubemapping && KeyboardData.data.wasPressed(Keyboard.KEY_C))
		{
			cubemapping = true;

			Vector3f[] positions = new Vector3f[]
			{
				new Vector3f(-20, -20, 10),
				new Vector3f(20, -20, 10),
				new Vector3f(20, 20, 10),
				new Vector3f(-20, 20, 10),
			};

			processQueue = new ProcessQueue();
			processQueue.addProcess(new CubemapProcess("dragon", positions));

			processQueue.start(this);
		}

		if (processQueue != null)
		{
			processQueue.update(timestamp, elapsedTime);
		}
	}

	@Override
	public void onCallback(ProcessQueue data)
	{
		cubemapping = false;
		processQueue.dispose();
		processQueue = null;
	}
}

package zyx.engine.components.cubemaps.saving;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.Stage;
import zyx.game.controls.process.BaseProcess;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.camera.Camera;

public class CubemapProcess extends BaseProcess
{

	private Vector3f[] positions;

	private Vector3f[] rotations;
	
	private int positionIndex;
	private int rotationIndex;
	private int delay = 0;

	private static final int STATE_SET_ANGLE = 0;
	private static final int STATE_WAIT = 1;
	private static final int STATE_SNAPSHOT = 2;
	private int state = STATE_SET_ANGLE;
	
	private CubemapRenderer cubemapRenderer;

	public CubemapProcess(String name, Vector3f[] positions)
	{
		this.positions = positions;

		rotations = new Vector3f[]
		{
			new Vector3f(90, -90, 90),		//Tail
			new Vector3f(90, 90, -90),		//Face
			new Vector3f(-90, 0, 0),		//Back bend
			new Vector3f(-90, 180, 180),	//Front bend
			new Vector3f(-180, 0, 0),		//Top
			new Vector3f(0, 0, 180),		//Bottom
		};
		
		cubemapRenderer = new CubemapRenderer(name + ".cube", positions);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		delay -= elapsedTime;

		if (delay <= 0)
		{
			switch (state)
			{
				case STATE_SET_ANGLE:
					Vector3f rot = rotations[rotationIndex];
					Vector3f pos = positions[positionIndex];
					
					Camera.getInstance().setPosition(false, pos);
					Camera.getInstance().setRotation(rot);
					state = STATE_SNAPSHOT;
					break;
				case STATE_SNAPSHOT:
					DeferredRenderer.getInstance().setCubemapRenderer(cubemapRenderer);
					state = STATE_WAIT;
					break;
				case STATE_WAIT:
					rotationIndex++;
					state = STATE_SET_ANGLE;
					break;
			}

			delay = 100;
		}

		if (rotationIndex >= rotations.length)
		{
			rotationIndex = 0;
			positionIndex++;
			
			if (positionIndex >= positions.length)
			{
				finish();
			}
		}
	}

	@Override
	protected void onFinish()
	{
		cubemapRenderer.writeToFile();
		Stage.instance.visible = true;
	}

	@Override
	protected void onStart()
	{
		Stage.instance.visible = false;
	}
}

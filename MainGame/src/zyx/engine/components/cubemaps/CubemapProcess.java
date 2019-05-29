package zyx.engine.components.cubemaps;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.Stage;
import zyx.game.controls.process.BaseProcess;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.camera.Camera;

public class CubemapProcess extends BaseProcess
{

	private Vector3f position;

	private Vector3f[] directions;
	private int index;

	private int delay = 0;

	private static final int STATE_SET_ANGLE = 0;
	private static final int STATE_WAIT = 1;
	private static final int STATE_SNAPSHOT = 2;
	private int state = STATE_SET_ANGLE;
	
	private CubemapRenderer cubemapRenderer;

	public CubemapProcess(Vector3f cameraPos)
	{
		position = new Vector3f(cameraPos);

		/*
			GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516,
			GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518,
			GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A,
		 */
		directions = new Vector3f[]
		{
			new Vector3f(90, -90, 90), //Seems correct, but with artifact?		Tail
			new Vector3f(90, 90, -90), //Seems correct, but with artifact?		Face
			new Vector3f(-90, 0, 0), //Seems correct							Back bend
			new Vector3f(-90, 180, 180), //										Front bend
			new Vector3f(-180, 0, 0), //Seems correct							Top
			new Vector3f(0, 0, 180), //Seems correct							Bottom
		};
		
		cubemapRenderer = new CubemapRenderer("dragon.cube");
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
					Vector3f dir = directions[index];
					Camera.getInstance().setRotation(dir);
					state = STATE_SNAPSHOT;
					break;
				case STATE_SNAPSHOT:
					DeferredRenderer.getInstance().setCubemapRenderer(cubemapRenderer);
					state = STATE_WAIT;
					break;
				case STATE_WAIT:
					index++;
					state = STATE_SET_ANGLE;
					break;
			}

			delay = 100;

		}

		if (index >= directions.length)
		{
			finish();
		}
	}

	@Override
	protected void onFinish()
	{
		cubemapRenderer.finalizeCubemap();
		Stage.instance.visible = true;
	}

	@Override
	protected void onStart()
	{
		Stage.instance.visible = false;
	}
}

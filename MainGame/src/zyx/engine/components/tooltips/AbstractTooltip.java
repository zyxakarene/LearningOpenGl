package zyx.engine.components.tooltips;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.utils.ScreenSize;
import zyx.game.components.screen.json.JsonSprite;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.FloatMath;

public abstract class AbstractTooltip extends JsonSprite
{

	private static final Matrix4f VIEW_PROJ = SharedShaderObjects.WORLD_PROJECTION_VIEW_TRANSFORM;

	private WorldObject target;
	private boolean onScreen;

	protected float minScale;
	protected boolean hideAtMinScale;
	
	public float currentDistance;

	public AbstractTooltip(WorldObject target)
	{
		super();

		this.target = target;
		this.onScreen = false;
		this.minScale = 1;
		this.hideAtMinScale = false;
	}

	@Override
	protected void onDraw()
	{
		if (onScreen)
		{
			super.onDraw();
		}
	}

	void getScreenPosition(Vector2f out, Vector3f cameraPos)
	{
		target.getPosition(false, HELPER_VEC3);

		HELPER_VEC4.x = HELPER_VEC3.x;
		HELPER_VEC4.y = HELPER_VEC3.y;
		HELPER_VEC4.z = HELPER_VEC3.z;
		HELPER_VEC4.w = 1;

		Matrix4f.transform(VIEW_PROJ, HELPER_VEC4, HELPER_VEC4);

		out.x = HELPER_VEC4.x / HELPER_VEC4.w;
		out.y = HELPER_VEC4.y / HELPER_VEC4.w;

		onScreen = HELPER_VEC4.z > 0;

		if (onScreen)
		{
			//Convert from [-1, 1] space to [0, 1]
			out.x = (out.x + 1) / 2;
			out.y = (out.y + 1) / 2;

			//Convert from [0, 1] space to [0, windowWidth]
			out.x = out.x * ScreenSize.windowWidth;

			//Convert from [0, 1] space to [0, windowHeight]
			out.y = ScreenSize.windowHeight - (out.y * ScreenSize.windowHeight);

			currentDistance = FloatMath.distance(HELPER_VEC3, cameraPos, true);
			float scale = 8f / currentDistance;
			scale = scale < minScale ? minScale : scale;
			scale = scale > 1 ? 1 : scale;
			
			onScreen = !hideAtMinScale || (hideAtMinScale && scale > minScale);

			if (onScreen)
			{
				setScale(scale, scale);
			}
		}
		
		touchable = onScreen;
		visible = onScreen;
		if (!onScreen)
		{
			currentDistance = Integer.MAX_VALUE;
		}
	}
}

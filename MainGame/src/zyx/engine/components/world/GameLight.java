package zyx.engine.components.world;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.game.controls.lights.LightsManager;
import zyx.opengl.lighs.ILight;
import zyx.utils.Color;

public class GameLight extends WorldObject implements ILight
{

	private int power;
	private int color;
	
	private Vector3f pos;
	private Vector3f colorVector;
	private boolean dirtyPos;

	public GameLight()
	{
		this(0xFFFFFF, 100);
	}
	
	public GameLight(int color, int power)
	{
		this.color = color;
		this.power = power;
		this.dirtyPos = true;
		this.pos = SharedPools.VECTOR_POOL_3F.getInstance();
		this.colorVector = SharedPools.VECTOR_POOL_3F.getInstance();
		
		Color.toVector(color, colorVector);

		LightsManager.getInstane().addLight(this);
	}

	@Override
	public int getPower()
	{
		return power;
	}

	@Override
	public Vector3f getLightPosition(Vector3f out)
	{
		if (dirtyPos)
		{
			super.getPosition(false, pos);
			dirtyPos = false;
		}
		
		out.set(pos);
		return out;
	}

	@Override
	public int getColor()
	{
		return color;
	}

	@Override
	public Vector3f getColorVector(Vector3f out)
	{
		out.set(colorVector);
		return out;
	}
	
	@Override
	protected void updateTransforms(boolean alsoChildren)
	{
		super.updateTransforms(alsoChildren);
		
		 dirtyPos = true;
	}

	@Override
	protected void onDispose()
	{
		LightsManager.getInstane().removeLight(this);
		SharedPools.VECTOR_POOL_3F.releaseInstance(pos);
		SharedPools.VECTOR_POOL_3F.releaseInstance(colorVector);
		
		pos = null;
		colorVector = null;
		
		super.onDispose();
	}

	@Override
	public String getDebugIcon()
	{
		return "light.png";
	}
}

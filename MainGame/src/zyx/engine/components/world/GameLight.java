package zyx.engine.components.world;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.game.controls.lights.LightsManager;
import zyx.opengl.lighs.ILight;
import zyx.opengl.shaders.implementations.Shader;

public class GameLight extends WorldObject implements ILight
{

	private float intensity;
	private int color;
	
	private Vector3f pos;
	private boolean dirtyPos;

	public GameLight()
	{
		this(0xFFFFFF, 1);
	}
	
	public GameLight(int color, float intensity)
	{
		super(Shader.WORLD);
		
		this.color = color;
		this.intensity = intensity;
		this.dirtyPos = true;
		this.pos = SharedPools.VECTOR_POOL_3F.getInstance();
		
		LightsManager.getInstane().addLight(this);
	}

	@Override
	protected void onDraw()
	{
	}

	@Override
	public float getIntensity()
	{
		return intensity;
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
		
		super.onDispose();
	}
}

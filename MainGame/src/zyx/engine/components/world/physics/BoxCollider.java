package zyx.engine.components.world.physics;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.utils.geometry.Box;

public class BoxCollider extends ParentMovingCollider
{

	//			6
	//		5		7
	//	X+	| 	4	|  Y+
	//		|	2	|
	//		1		3
	//		  	0
	private Vector3f[] verticies;
	Box boundingBox;

	private float width;
	private float debth;
	private float height;

	public BoxCollider(float width, float debth, float height, boolean isStatic)
	{
		super(isStatic);

		this.width = width;
		this.debth = debth;
		this.height = height;
		this.verticies = new Vector3f[8];

		for (int i = 0; i < verticies.length; i++)
		{
			verticies[i] = SharedPools.VECTOR_POOL.getInstance();
		}
		float wH = width / 2;
		float dH = debth / 2;

		verticies[0].set(-wH, -dH, 0);
		verticies[1].set(wH, -dH, 0);
		verticies[2].set(wH, dH, 0);
		verticies[3].set(-wH, dH, 0);
		verticies[4].set(-wH, -dH, height);
		verticies[5].set(wH, -dH, height);
		verticies[6].set(wH, dH, height);
		verticies[7].set(-wH, dH, height);

		boundingBox = new Box(width, debth, height);

	}

	public BoxCollider(float width, float debth, float height)
	{
		this(width, debth, height, true);
	}

	@Override
	public void onUpdate(long timestamp, int elapsedTime)
	{
		if (parent != null)
		{
			updateBoundingBox();
		}
	}

	@Override
	protected void onParentSet()
	{
		updateBoundingBox();
	}

	@Override
	protected void onParentCleared()
	{
		boundingBox.minX = 0;
		boundingBox.maxX = 0;
		boundingBox.minY = 0;
		boundingBox.maxY = 0;
		boundingBox.minZ = 0;
		boundingBox.maxZ = 0;
	}

	private void updateBoundingBox()
	{
		Vector3f parentPos = parent.getPosition();
		float wH = width / 2;
		float dH = debth / 2;

		boundingBox.minX = parentPos.x - wH;
		boundingBox.maxX = parentPos.x + wH;
		boundingBox.minY = parentPos.y - dH;
		boundingBox.maxY = parentPos.y + dH;
		boundingBox.minZ = parentPos.z;
		boundingBox.maxZ = parentPos.z + height;
	}

	@Override
	protected void onDispose()
	{
		for (int i = 0; i < verticies.length; i++)
		{
			SharedPools.VECTOR_POOL.releaseInstance(verticies[i]);
			verticies[i] = null;
		}

		verticies = null;
	}

}

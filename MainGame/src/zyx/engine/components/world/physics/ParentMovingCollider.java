package zyx.engine.components.world.physics;

import zyx.engine.components.world.Collider;

abstract class ParentMovingCollider extends Collider
{

	ParentMovingCollider(boolean isStatic)
	{
		super(isStatic);
	}

	void updateParentPos(float x, float y, float z)
	{
		if (parent != null)
		{
			parent.setPosition(true, x, y, z);
		}
	}

	void updateParentPosX(float x)
	{
		if (parent != null)
		{
			parent.setX(x);
			onParentSet();
		}
	}

	void updateParentPosY(float y)
	{
		if (parent != null)
		{
			parent.setY(y);
			onParentSet();
		}
	}

	void updateParentPosZ(float z)
	{
		if (parent != null)
		{
			parent.setZ(z);
			onParentSet();
		}
	}
}

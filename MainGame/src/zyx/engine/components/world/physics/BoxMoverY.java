package zyx.engine.components.world.physics;

import zyx.utils.geometry.Box;

public class BoxMoverY extends AbstractColliderMover
{

	@Override
	void onMoveTo(Box statBound, BoxCollider physCollider, boolean intoGround)
	{
		physBound = physCollider.boundingBox;

		if (intoGround)
		{
			//Hit ground
			physCollider.updateParentPosY(statBound.maxY + physBound.debth / 2);
			physCollider.velocity.y = 0;
		}
		else
		{
			//Hit ceiling
			physCollider.updateParentPosY(statBound.minY - physBound.debth / 2);
			physCollider.velocity.y = 0;
		}
	}

}

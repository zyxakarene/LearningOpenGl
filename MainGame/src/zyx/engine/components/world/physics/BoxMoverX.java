package zyx.engine.components.world.physics;

import zyx.utils.geometry.Box;

public class BoxMoverX extends AbstractColliderMover
{

	@Override
	void onMoveTo(Box statBound, BoxCollider physCollider, boolean intoGround)
	{
		physBound = physCollider.boundingBox;

		if (intoGround)
		{
			//Hit ground
			physCollider.updateParentPosX(statBound.maxX + physBound.width / 2);
			physCollider.velocity.x = 0;
		}
		else
		{
			//Hit ceiling
			physCollider.updateParentPosX(statBound.minX - physBound.width / 2);
			physCollider.velocity.x = 0;
		}
	}

}

package zyx.engine.components.world.physics;

import zyx.utils.geometry.Box;

public class BoxMoverZ extends AbstractColliderMover
{

	@Override
	 void onMoveTo(Box statBound, BoxCollider physCollider, boolean intoGround)
	{
		physBound = physCollider.boundingBox;
		
		if (intoGround)
		{
			//Hit ground
			physCollider.updateParentPosZ(statBound.maxZ);
			physCollider.velocity.z = 0;
		}
		else
		{
			//Hit ceiling
			physCollider.updateParentPosZ(statBound.minZ - physBound.height);
			physCollider.velocity.z = 0;
		}
	}

}

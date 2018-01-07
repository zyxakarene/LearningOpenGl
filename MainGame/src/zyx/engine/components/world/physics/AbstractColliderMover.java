package zyx.engine.components.world.physics;

import zyx.utils.geometry.Box;

public abstract class AbstractColliderMover
{

	protected Box physBound = null;

	abstract void onMoveTo(Box statBound, BoxCollider physCollider, boolean intoGround);
}

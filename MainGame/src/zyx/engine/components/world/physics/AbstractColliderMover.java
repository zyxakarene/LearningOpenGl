package zyx.engine.components.world.physics;

public abstract class AbstractColliderMover
{

	protected PhysicsBoundingBox physBound = null;

	abstract void onMoveTo(PhysicsBoundingBox statBound, BoxCollider physCollider, boolean intoGround);
}

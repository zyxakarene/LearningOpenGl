package zyx.game.components.world.player;

import zyx.engine.components.world.complexphysics.EntityCollider;
import zyx.game.components.MeshObject;

public class Player extends MeshObject
{

	public Player()
	{
		load("mesh.box");

		setScale(0.001f, 0.001f, 0.2f);
		setCollider(new EntityCollider(10));

		addBehavior(new PlayerMovementBehavior());

		drawable = true;
	}
}

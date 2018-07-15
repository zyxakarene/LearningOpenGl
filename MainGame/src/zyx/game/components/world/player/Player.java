package zyx.game.components.world.player;

import zyx.engine.components.world.physics.BoxCollider;
import zyx.game.components.MeshObject;

public class Player extends MeshObject
{

	public Player()
	{
		load("mesh.box");

		setScale(0.25f, 0.25f, 1);
		setCollider(new BoxCollider(10, 10, 40, false));

		addBehavior(new PlayerMovementBehavior());

		drawable = false;
	}
}

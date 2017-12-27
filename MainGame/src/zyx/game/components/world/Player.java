package zyx.game.components.world;

import zyx.game.components.GameObject;

public class Player extends GameObject
{

	public Player()
	{
		load("assets/models/box.zaf");
		
		addBehavior(new PlayerMovementBehavior());
	}

}

package zyx.game.components.world.player;

import zyx.engine.components.world.physics.BoxCollider;
import zyx.game.components.GameObject;

public class Player extends GameObject
{

	public Player()
	{
		load("assets/models/box.zaf");

		setScale(0.25f, 0.25f, 1);
		setCollider(new BoxCollider(10, 10, 40, false));

		addBehavior(new PlayerMovementBehavior());
		
		drawable = false;
	}

	@Override
	protected void onDraw()
	{
		super.onDraw(); //To change body of generated methods, choose Tools | Templates.
	}
	
	

}

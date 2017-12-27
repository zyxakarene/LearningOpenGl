package zyx.game.components.world;

import org.lwjgl.input.Keyboard;
import zyx.engine.components.world.Collider;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.KeyboardData;

class PlayerMovementBehavior extends Behavior
{

	private Collider collider;
	private KeyboardData keyboard;

	public PlayerMovementBehavior()
	{
		super(BehaviorType.PLAYER_MOVEMENT);
	}

	@Override
	public void initialize()
	{
		collider = gameObject.getCollider();
		keyboard = KeyboardData.data;
	}

	
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (collider == null)
		{
			collider = gameObject.getCollider();
			return;
		}
		
		if (keyboard.isDown(Keyboard.KEY_UP))
		{
			collider.velocity.x -= 10;
		}
		else if (keyboard.isDown(Keyboard.KEY_DOWN))
		{
			collider.velocity.x += 10;
		}
		
		if (keyboard.isDown(Keyboard.KEY_RIGHT))
		{
			collider.velocity.y += 10;
		}
		else if (keyboard.isDown(Keyboard.KEY_LEFT))
		{
			collider.velocity.y -= 10;
		}
		
		if (keyboard.wasPressed(Keyboard.KEY_SPACE))
		{
			collider.velocity.z += 100;
		}
	}

}

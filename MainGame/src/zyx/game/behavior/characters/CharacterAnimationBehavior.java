package zyx.game.behavior.characters;

import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.world.characters.CharacterInfo;
import zyx.game.components.world.characters.GameCharacter;

public class CharacterAnimationBehavior extends Behavior<GameCharacter>
{

	private CharacterInfo info;
	private AnimState state;

	public CharacterAnimationBehavior()
	{
		super(BehaviorType.CHARACTER_ANIMATOR);
	}

	@Override
	public void initialize()
	{
		info = gameObject.info;
		state = AnimState.UNKNOWN;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		AnimState newState;
		
		if (info.moving)
		{
			if (info.heldItem != null)
			{
				newState = AnimState.WALKING_CARRY;
			}
			else
			{
				newState = AnimState.WALKING;
			}
		}
		else if (info.interacting)
		{
			if (state != AnimState.INTERACTING_LOOP)
			{
				newState = AnimState.INTERACTING_IN;
			}
			else
			{
				newState = AnimState.INTERACTING_LOOP;
			}
		}
		else if (info.eating)
		{
			newState = AnimState.EATING;
		}
		else if (info.sitting)
		{
			newState = AnimState.SITTING;
		}
		else if (info.heldItem != null)
		{
			newState = AnimState.IDLE_CARRY;
		}
		else
		{
			newState = AnimState.IDLE;
		}
		
		if (newState != state)
		{
			state = newState;
			gameObject.setAnimation(state.animation);
		}
	}

	private enum AnimState
	{
		UNKNOWN("idle"),
		IDLE("idle"),
		IDLE_CARRY("idleCarry"),
		SITTING("sitting"),
		EATING("eating"),
		WALKING("walk"),
		WALKING_CARRY("walkCarry"),
		INTERACTING_IN("actionIn"),
		INTERACTING_LOOP("actionLoop");
		
		private final String animation;

		private AnimState(String animation)
		{
			this.animation = animation;
		}
	}
}

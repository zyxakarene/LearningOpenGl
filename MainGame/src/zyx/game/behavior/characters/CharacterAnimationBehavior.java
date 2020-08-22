package zyx.game.behavior.characters;

import zyx.engine.utils.callbacks.ICallback;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.IAnimatedMesh;
import zyx.game.components.world.characters.CharacterInfo;
import zyx.game.components.world.characters.GameCharacter;
import zyx.game.components.world.furniture.Chair;

public class CharacterAnimationBehavior extends Behavior<GameCharacter> implements ICallback<String>
{

	private CharacterInfo info;
	private AnimState state;
	private IAnimatedMesh animatedMesh;

	public CharacterAnimationBehavior()
	{
		super(BehaviorType.CHARACTER_ANIMATOR);
	}

	@Override
	public void initialize()
	{
		info = gameObject.info;
		state = AnimState.UNKNOWN;

		animatedMesh = gameObject.getAnimatedMesh();
		animatedMesh.addAnimationCompletedCallback(this);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		AnimState newState = AnimState.UNKNOWN;

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
		else if (info.interacting != null)
		{
			if (info.interacting instanceof Chair)
			{
				if (info.eating)
				{
					newState = AnimState.EATING;
				}
				else
				{
					newState = AnimState.SITTING;
				}
			}
			else if (state != AnimState.INTERACTING_LOOP)
			{
				newState = AnimState.INTERACTING_IN;
			}
		}
		else if (info.heldItem != null)
		{
			newState = AnimState.IDLE_CARRY;
		}
		else
		{
			newState = AnimState.IDLE;
		}

		if (newState != AnimState.UNKNOWN)
		{
			setState(newState);
		}
	}

	@Override
	public void onCallback(String data)
	{
		if (state == AnimState.INTERACTING_IN)
		{
			setState(AnimState.INTERACTING_LOOP);
		}
	}

	private void setState(AnimState newState)
	{
		if (newState != state)
		{
			state = newState;
			animatedMesh.setAnimation(state.animation);
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();

		animatedMesh.removeAnimationCompletedCallback(this);
	}

	private enum AnimState
	{
		UNKNOWN(null),
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

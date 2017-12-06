package zyx.opengl.models.implementations.bones.animation;

import zyx.utils.DeltaTime;
import zyx.utils.cheats.Print;

public class AnimationController
{
	String animation;
	long animationStartedAt;

	public AnimationController(String animation)
	{
		setAnimation(animation);
	}
	
	public final void setAnimation(String name)
	{
		animation = name;
		animationStartedAt = DeltaTime.getTimestamp();
		Print.out("Animate started at", animationStartedAt);
	}
}

package zyx.game.components;

import zyx.opengl.models.implementations.bones.animation.AnimationController;

public class AnimatedMesh extends SimpleMesh
{

	private AnimationController animationController;
	private String animation;

	public AnimatedMesh()
	{
		animationController = new AnimationController();
	}

	public void setAnimation(String animation)
	{
		this.animation = animation;
		animationController.setAnimation(animation);
	}

	public String getAnimation()
	{
		return animation;
	}

	@Override
	protected void onDraw()
	{
		if (loaded)
		{
			model.setAnimation(animationController);
		}

		super.onDraw();
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();

		if (animationController != null)
		{
			animationController.dispose();
			animationController = null;
		}
	}

}

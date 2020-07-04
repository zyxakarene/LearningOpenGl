package zyx.game.components;

import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.utils.cheats.Print;

public class AnimatedMesh extends SimpleMesh
{

	private AnimationController animationController;
	private String animation;
	
	public boolean debugging;

	public AnimatedMesh()
	{
		animationController = new AnimationController();
	}

	public void setAnimation(String animation)
	{
		this.animation = animation;
		animationController.setAnimation(animation);
	}
	
	public void clearBlend()
	{
		animationController.clearBlend();
	}

	public String getAnimation()
	{
		return animation;
	}

	@Override
	protected void onDraw()
	{
		if (model != null && model.ready)
		{
			if (debugging)
			{
				Print.out();
			}
			
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
	
	@Override
	public String getDebugIcon()
	{
		return "animatedmesh.png";
	}

}

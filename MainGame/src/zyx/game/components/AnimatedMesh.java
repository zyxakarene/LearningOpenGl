package zyx.game.components;

import zyx.engine.utils.callbacks.ICallback;
import zyx.opengl.models.implementations.bones.animation.AnimationController;

public class AnimatedMesh extends SimpleMesh implements IAnimatedMesh, ICallback<SimpleMesh>
{

	private AnimationController animationController;
	private String animation;
	
	public AnimatedMesh()
	{
		animationController = new AnimationController();		
		onLoaded(this);
	}

	@Override
	public void setAnimation(String animation)
	{
		this.animation = animation;
		animationController.setAnimation(animation);
	}
	
	@Override
	public void addAnimationCompletedCallback(ICallback<String> callback)
	{
		animationController.addAnimationCompletedCallback(callback);
	}
	
	@Override
	public void removeAnimationCompletedCallback(ICallback<String> callback)
	{
		animationController.removeAnimationCompletedCallback(callback);
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

	@Override
	public void onCallback(SimpleMesh data)
	{
		renderer.setAnimation(animationController);
	}
}

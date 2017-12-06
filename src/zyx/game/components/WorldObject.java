package zyx.game.components;

import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.loading.bones.ZafLoader;
import zyx.utils.interfaces.IDrawable;

public class WorldObject extends GameObject implements IDrawable
{

	private final WorldModel model;
	private AnimationController animationController;

	public WorldObject(String animation)
	{
		model = ZafLoader.loadFromZaf("knight.zaf");
		animationController = new AnimationController(animation);
	}

	public void setAnimation(String name)
	{
		animationController.setAnimation(name);
	}

	@Override
	public void draw()
	{
		model.setAnimation(animationController);
		
		model.transform(position, rotation, scale);
		model.draw();
	}
}

package zyx.game.components;

import org.lwjgl.util.vector.Matrix4f;
import zyx.game.components.world.model.LoadableModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.utils.interfaces.IDrawable;

public class WorldObject extends GameObject implements IDrawable
{

	private final LoadableModel model;
	private AnimationController animationController;

	public WorldObject(String animation)
	{
		model = new LoadableModel();
		animationController = new AnimationController();
	}
	
	public void load(String path)
	{
		model.load(path);
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

	public Joint getAttatchment(String name)
	{
		return model.getAttatchment(name);
	}

	public void setPos(Matrix4f attatchPoint)
	{
		model.setPos(attatchPoint);
	}
}

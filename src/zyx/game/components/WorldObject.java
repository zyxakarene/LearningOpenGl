package zyx.game.components;

import java.util.ArrayList;
import zyx.game.components.world.model.LoadableModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.utils.interfaces.IDrawable;

public class WorldObject extends GameObject implements IDrawable
{

	private final LoadableModel model;
	private AnimationController animationController;

	private ArrayList<WorldObject> attachments;
	
	public WorldObject(String animation)
	{
		model = new LoadableModel();
		animationController = new AnimationController();
		attachments = new ArrayList<>();
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
	
	public void addAttachment(WorldObject child, String attachmentPoint)
	{
		attachments.add(child);
		model.addAttachment(child.model, child.animationController, this, attachmentPoint);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		super.update(timestamp, elapsedTime);
		
		for (WorldObject attachment : attachments)
		{
			attachment.update(timestamp, elapsedTime);
		}
	}
}

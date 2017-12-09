package zyx.game.components;

import java.util.ArrayList;
import zyx.game.controls.models.ModelManager;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.attachments.Attachment;
import zyx.utils.interfaces.IDrawable;

public class WorldObject extends GameObject implements IDrawable, IResourceLoaded<WorldModel>
{

	private boolean loaded;
	private WorldModel model;
	private AnimationController animationController;

	private ArrayList<WorldObject> attachedObjects;
	private ArrayList<Attachment> attachments;

	public WorldObject(String animation)
	{
		animationController = new AnimationController();
		attachedObjects = new ArrayList<>();
		attachments = new ArrayList<>();
		loaded = false;
	}

	public void load(String path)
	{
		ModelManager.getInstance().loadModel(path, this);
	}

	@Override
	public void resourceLoaded(WorldModel data)
	{
		model = data;
		loaded = true;
	}

	public void setAnimation(String name)
	{
		animationController.setAnimation(name);
	}

	@Override
	public void draw()
	{
		if (loaded)
		{
			model.setAnimation(animationController);

			model.transform(position, rotation, scale);
			model.draw();

			for (Attachment attachment : attachments)
			{
				attachment.child.drawAsAttachment(attachment);
			}
		}
	}
	
	private void drawAsAttachment(Attachment attachment)
	{
		model.drawAsAttachment(attachment);
		
		for (Attachment attachment2 : attachments)
		{
			attachment2.child.drawAsAttachment(attachment2);
		}
	}

	public void addAttachment(WorldObject child, String attachmentPoint)
	{
		Attachment attachment = new Attachment();
		attachment.child = child;
		attachment.parent = this;
		attachment.animations = animationController;
		attachment.position = this;
		attachment.joint = model.getBoneByName(attachmentPoint);

		attachments.add(attachment);
		attachedObjects.add(child);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		super.update(timestamp, elapsedTime);

		for (WorldObject attachment : attachedObjects)
		{
			attachment.update(timestamp, elapsedTime);
		}
	}
}

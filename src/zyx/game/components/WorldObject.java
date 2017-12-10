package zyx.game.components;

import java.util.ArrayList;
import java.util.LinkedList;
import zyx.game.controls.models.ModelManager;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.attachments.Attachment;
import zyx.opengl.models.implementations.bones.attachments.AttachmentRequest;
import zyx.utils.interfaces.IDrawable;

public class WorldObject extends GameObject implements IDrawable, IResourceLoaded<WorldModel>
{

	private boolean loaded;
	private WorldModel model;
	private AnimationController animationController;

	private ArrayList<WorldObject> attachedObjects;
	private ArrayList<Attachment> attachments;

	private LinkedList<AttachmentRequest> attachmentRequests;

	public WorldObject()
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

		if (attachmentRequests != null)
		{
			AttachmentRequest request;
			while (attachmentRequests.isEmpty() == false)
			{
				request = attachmentRequests.remove();
				addAttachment(request.child, request.attachmentPoint);
			}
		}
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
		if (loaded)
		{
			model.drawAsAttachment(attachment);

			for (Attachment attachment2 : attachments)
			{
				attachment2.child.drawAsAttachment(attachment2);
			}
		}
	}

	public void addAttachment(WorldObject child, String attachmentPoint)
	{
		if (loaded)
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
		else
		{
			if (attachmentRequests == null)
			{
				attachmentRequests = new LinkedList<>();
			}

			AttachmentRequest request = new AttachmentRequest(child, attachmentPoint);
			attachmentRequests.add(request);
		}
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

	@Override
	public void dispose()
	{
		super.dispose();
		
		for (WorldObject attachedObject : attachedObjects)
		{
			attachedObject.dispose();
		}
		
		attachments.clear();
		attachedObjects.clear();
		
		if (attachmentRequests != null)
		{
			attachmentRequests.clear();
		}
		
		animationController = null;
		model = null;
		attachments = null;
		attachedObjects = null;
		attachmentRequests = null;
	}
}

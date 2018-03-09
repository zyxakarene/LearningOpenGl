package zyx.game.components;

import zyx.engine.components.world.WorldObject;
import java.util.ArrayList;
import java.util.LinkedList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorBundle;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.models.ModelManager;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.SharedWorldModelTransformation;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.attachments.Attachment;
import zyx.opengl.models.implementations.bones.attachments.AttachmentRequest;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.cheats.DebugPhysics;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;
import zyx.utils.interfaces.IUpdateable;

public class GameObject extends WorldObject implements IUpdateable, IPhysbox, IResourceLoaded<WorldModel>
{
	private static Vector3f HELPER_POS = new Vector3f();
	private static Vector3f HELPER_ROT = new Vector3f();
	private static Vector3f HELPER_SCALE = new Vector3f();
	
	private String path;

	private boolean loaded;
	private WorldModel model;
	private AnimationController animationController;

	private ArrayList<GameObject> attachedObjects;
	private ArrayList<Attachment> attachments;
	private LinkedList<AttachmentRequest> attachmentRequests;

	private BehaviorBundle behaviors;
	private AbstractTexture overwriteTexture;
	
	private PhysBox physbox;

	public GameObject()
	{
		behaviors = new BehaviorBundle(this);

		animationController = new AnimationController();
		attachedObjects = new ArrayList<>();
		attachments = new ArrayList<>();
		loaded = false;
	}

	public void load(String path)
	{
		this.path = path;
		ModelManager.getInstance().loadModel(path, this);
	}

	@Override
	public void resourceLoaded(WorldModel data)
	{
		physbox = data.getPhysbox();
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
		
		DebugPhysics.getInstance().registerPhysbox(this);
	}

	public void setAnimation(String name)
	{
		animationController.setAnimation(name);
	}

	@Override
	protected void onTransform()
	{
		getPosition(true, HELPER_POS);
		getRotation(true, HELPER_ROT);
		getScale(true, HELPER_SCALE);
		
		if (loaded)
		{
			model.setAnimation(animationController);
		}
		
		SharedWorldModelTransformation.transform(HELPER_POS, HELPER_ROT, HELPER_SCALE);
		
		shader.bind();
		shader.upload();
	}

	@Override
	protected void updateWorldMatrix()
	{
		super.updateWorldMatrix();
		
		if (loaded)
		{
			for (GameObject attachedObject : attachedObjects)
			{
				attachedObject.updateWorldMatrix();
			}
		}
	}
	
	@Override
	protected void onDraw()
	{
		if (loaded)
		{
			if (overwriteTexture != null)
			{
				model.setOverwriteTexture(overwriteTexture);
			}

			
			model.setAnimation(animationController);
			model.draw();
			
			DebugPhysics.getInstance().draw(this);
			
			model.setOverwriteTexture(null);

			Attachment attachment;
			int len = attachments.size();
			for (int i = 0; i < len; i++)
			{
				attachment = attachments.get(i);
				attachment.child.drawAsAttachment(attachment);
			}
		}
	}

	private void drawAsAttachment(Attachment attachment)
	{
		WorldShader.MATRIX_MODEL.load(attachment.parent.worldMatrix);
		shader.upload();
		
		if (loaded)
		{
			onTransform();
			
			model.setAnimation(animationController);
			model.drawAsAttachment(attachment);

			Attachment subAttachment;
			int len = attachments.size();
			for (int i = 0; i < len; i++)
			{
				subAttachment = attachments.get(i);
				subAttachment.child.drawAsAttachment(subAttachment);
			}
		}
	}

	public void addAttachment(GameObject child, String attachmentPoint)
	{
		if (loaded)
		{
			Joint attachJoint = model.getBoneByName(attachmentPoint);
			if (attachJoint == null)
			{
				Print.out("Warning: No such bone", attachmentPoint, "on", this);
			}
			else
			{
				Attachment attachment = new Attachment();
				attachment.child = child;
				attachment.parent = this;
				attachment.joint = model.getBoneByName(attachmentPoint);

				attachments.add(attachment);
				attachedObjects.add(child);
			}
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
	public final void update(long timestamp, int elapsedTime)
	{
		behaviors.update(timestamp, elapsedTime);

		for (GameObject attachment : attachedObjects)
		{
			attachment.update(timestamp, elapsedTime);
		}
		
		onUpdate(timestamp, elapsedTime);
	}
	
	protected void onUpdate(long timestamp, int elapsedTime)
	{
	}

	public final void addBehavior(Behavior behavior)
	{
		behaviors.addBehavior(behavior);
	}

	public Behavior getBehaviorById(BehaviorType type)
	{
		return behaviors.getBehaviorById(type);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		for (GameObject attachedObject : attachedObjects)
		{
			attachedObject.dispose();
		}

		attachments.clear();
		attachedObjects.clear();

		if (attachmentRequests != null)
		{
			attachmentRequests.clear();
		}

		behaviors.dispose();

		animationController = null;
		model = null;
		attachments = null;
		attachedObjects = null;
		attachmentRequests = null;
		behaviors = null;
	}

	@Override
	public String toString()
	{
		return String.format("WorldObject{%s, playing animation: %s}", path, animationController);
	}

	public void setTexture(AbstractTexture tex)
	{
		overwriteTexture = tex;
	}

	public AbstractTexture getTexture()
	{
		return model.getTexture();
	}

	@Override
	public PhysBox getPhysbox()
	{
		return physbox;
	}

	@Override
	public Matrix4f getMatrix()
	{
		return worldMatrix;
	}
}

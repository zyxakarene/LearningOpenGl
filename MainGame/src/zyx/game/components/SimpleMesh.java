package zyx.game.components;

import java.util.ArrayList;
import java.util.LinkedList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.MeshResource;
import zyx.engine.resources.impl.Resource;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.engine.utils.callbacks.ICallback;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.attachments.Attachment;
import zyx.opengl.models.implementations.bones.attachments.AttachmentRequest;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.cheats.DebugPhysics;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;

public class SimpleMesh extends WorldObject implements IPhysbox, IResourceReady<MeshResource>
{
	protected String resource;
	protected boolean loaded;
	protected WorldModel model;

	private PhysBox physbox;
	private Resource modelResource;

	private CustomCallback<SimpleMesh> onLoaded;

	private ArrayList<WorldObject> attachedObjects;
	private ArrayList<Attachment> attachments;
	private LinkedList<AttachmentRequest> attachmentRequests;

	private AbstractTexture overwriteTexture;

	public SimpleMesh()
	{
		super(Shader.WORLD);

		loaded = false;
		onLoaded = new CustomCallback<>(true);

		attachedObjects = new ArrayList<>();
		attachments = new ArrayList<>();
	}

	public void load(String resource)
	{
		this.resource = resource;

		modelResource = ResourceManager.getInstance().getResource(resource);
		modelResource.registerAndLoad(this);
	}

	@Override
	protected void onDraw()
	{
		if (loaded && inView())
		{
			shader.bind();
			shader.upload();

			if (overwriteTexture != null)
			{
				model.setOverwriteTexture(overwriteTexture);
			}

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
		
	@Override
	public float getRadius()
	{
		return loaded ? model.getRadius() : 0;
	}

	@Override
	public void getCenter(Vector3f out)
	{
		if (loaded)
		{
			out.set(model.getRadiusCenter());
		}
		else
		{
			out.set(0, 0, 0);
		}
	}
	
	private void drawAsAttachment(Attachment attachment)
	{
		Matrix4f.mul(attachment.parent.worldMatrix(), attachment.joint.getAttachmentTransform(), localMatrix);
		updateTransforms(true);

		draw();
	}

	public void onLoaded(ICallback<SimpleMesh> callback)
	{
		if (loaded)
		{
			callback.onCallback(this);
		}
		else
		{
			onLoaded.addCallback(callback);
		}
	}

	public void addChildAsAttachment(SimpleMesh child, String attachmentPoint)
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
				attachment.joint = attachJoint;

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
	public void onResourceReady(MeshResource resource)
	{
		WorldModel data = resource.getContent();

		physbox = data.getPhysbox();
		model = data;
		loaded = true;

		DebugPhysics.getInstance().registerPhysbox(this);

		onLoaded.dispatch(this);

		if (attachmentRequests != null)
		{
			while (!attachmentRequests.isEmpty())
			{
				AttachmentRequest request = attachmentRequests.remove();
				addChildAsAttachment(request.child, request.attachmentPoint);
			}

			attachmentRequests = null;
		}
	}

	@Override
	protected void onDispose()
	{
		if (modelResource != null)
		{
			modelResource.unregister(this);
			modelResource = null;
		}

		if (onLoaded != null)
		{
			onLoaded.dispose();
			onLoaded = null;
		}

		DebugPhysics.getInstance().unregisterPhysbox(this);

		model = null;
		physbox = null;

	}

	@Override
	public PhysBox getPhysbox()
	{
		return physbox;
	}

	@Override
	public Matrix4f getMatrix()
	{
		return worldMatrix();
	}

	@Override
	public Matrix4f getBoneMatrix(int boneId)
	{
		return model.getBoneById(boneId).getPhysTransform();
	}
}

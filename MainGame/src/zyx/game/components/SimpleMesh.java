package zyx.game.components;

import java.util.ArrayList;
import java.util.LinkedList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.cubemaps.IReflective;
import zyx.engine.components.world.WorldObject;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.meshes.MeshResource;
import zyx.engine.resources.impl.Resource;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.engine.utils.callbacks.ICallback;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.bones.attachments.Attachment;
import zyx.opengl.models.implementations.bones.attachments.AttachmentRequest;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.renderers.wrappers.WorldModelWrapper;
import zyx.utils.GeometryUtils;
import zyx.utils.cheats.DebugPhysics;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;

public class SimpleMesh extends WorldObject implements IPhysbox, IResourceReady<MeshResource>, IReflective
{

	protected String resource;
	protected boolean loaded;
	protected WorldModelWrapper wrapper;

	private PhysBox physbox;
	private Resource modelResource;
	private int cubemapIndex;

	private CustomCallback<SimpleMesh> onLoaded;

	private LinkedList<AttachmentRequest> attachmentRequests;
	private ArrayList<Attachment> attachments;
	private Attachment attachmentInfo;

	public SimpleMesh()
	{
		loaded = false;
		onLoaded = new CustomCallback<>(true);

		enableCubemaps();
	}

	public void load(String resource)
	{
		this.resource = resource;

		if (modelResource != null)
		{
			modelResource.unregister(this);
			modelResource = null;
		}

		modelResource = ResourceManager.getInstance().getResource(resource);
		modelResource.registerAndLoad(this);
	}

	public String getResource()
	{
		return resource;
	}
	
	public WorldModelMaterial cloneMaterial()
	{
		if (wrapper != null)
		{
			return wrapper.cloneMaterial(0);
		}
		
		return null;
	}

	@Override
	protected void updateTransforms(boolean alsoChildren)
	{
		super.updateTransforms(alsoChildren);

		CubemapManager.getInstance().dirtyPosition(this);
	}

	@Override
	public float getRadius()
	{
		return loaded ? wrapper.getRadius() : 0;
	}

	@Override
	public void getCenter(Vector3f out)
	{
		if (loaded)
		{
			out.set(wrapper.getRadiusCenter());
		}
		else
		{
			out.set(0, 0, 0);
		}
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

	@Override
	public Matrix4f worldMatrix()
	{
		if (attachmentInfo != null)
		{
			Matrix4f.mul(attachmentInfo.parent.worldMatrix(), attachmentInfo.lastMatrix, worldMatrix);
			
			return worldMatrix;
		}
		else
		{
			return super.worldMatrix();
		}
	}

	@Override
	public void onPostDraw()
	{
		if (attachments != null)
		{
			for (Attachment attachment : attachments)
			{
				attachment.loadBoneTransform();
			}
		}
	}
	
	public void attachTo(SimpleMesh parent, String attachmentPoint)
	{
		parent.addChildAsAttachment(this, attachmentPoint);
	}
		
	public void addChildAsAttachment(SimpleMesh child, String attachmentPoint)
	{
		if (attachments == null)
		{
			attachments = new ArrayList<>();
		}
		
		if (loaded)
		{
			Joint attachJoint = wrapper.getBoneByName(attachmentPoint);
			if (attachJoint == null)
			{
				Print.out("Warning: No such bone", attachmentPoint, "on", this);
			}
			else
			{
				child.setPosition(true, 0, 0, 0);
				child.setDir(true, GeometryUtils.ROTATION_X);
				
				child.attachmentInfo = new Attachment();
				child.attachmentInfo.child = child;
				child.attachmentInfo.parent = this;
				child.attachmentInfo.joint = attachJoint;
				
				attachments.add(child.attachmentInfo);
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

	public void removeChildAsAttachment(SimpleMesh child)
	{
		if (attachments != null)
		{
			attachments.remove(child.attachmentInfo);
		}
		
		child.setPosition(true, 0, 0, 0);
		child.setDir(true, GeometryUtils.ROTATION_X);
		child.attachmentInfo = null;

		if (attachmentRequests != null)
		{
			for (AttachmentRequest attachmentRequest : attachmentRequests)
			{
				if (attachmentRequest.child == child)
				{
					attachmentRequests.remove(attachmentRequest);
					break;
				}
			}
		}
	}

	@Override
	public void onResourceReady(MeshResource resource)
	{
		wrapper = resource.getContent();
		wrapper.setup(this);
		wrapper.setCubemapIndex(cubemapIndex);
		
		physbox = wrapper.getPhysbox();
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

	public void clean()
	{
		if (modelResource != null)
		{
			modelResource.unregister(this);
			modelResource = null;
		}

		DebugPhysics.getInstance().unregisterPhysbox(this);

		if (attachmentRequests != null)
		{
			attachmentRequests.clear();
			attachmentRequests = null;
		}

		if (wrapper != null)
		{
			wrapper.dispose();
			wrapper = null;
		}
		
		physbox = null;
		loaded = false;
	}

	@Override
	protected void onDispose()
	{
		disableCubemaps();

		if (onLoaded != null)
		{
			onLoaded.dispose();
			onLoaded = null;
		}

		clean();
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
	public WorldObject getWorldObject()
	{
		return this;
	}

	@Override
	public Matrix4f getBoneMatrix(int boneId)
	{
		return wrapper.getBoneById(boneId).getPhysTransform();
	}

	@Override
	public void enableCubemaps()
	{
		CubemapManager.getInstance().addItem(this);
	}

	@Override
	public void disableCubemaps()
	{
		CubemapManager.getInstance().removeItem(this);
	}

	@Override
	public void setCubemapIndex(int index)
	{
		cubemapIndex = index;
		if (wrapper != null)
		{
			wrapper.setCubemapIndex(cubemapIndex);
		}
	}

	@Override
	public String getDebugIcon()
	{
		return "mesh.png";
	}
}

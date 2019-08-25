package zyx.game.components;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.utils.GeometryUtils;
import zyx.utils.interfaces.IPhysbox;

public class MeshObject extends GameObject implements IPhysbox
{

	public SimpleMesh mesh;

	public MeshObject()
	{
		this(false);
	}

	public MeshObject(boolean animated)
	{
		if (animated)
		{
			mesh = new AnimatedMesh();
		}
		else
		{
			mesh = new SimpleMesh();
		}
		addChild(mesh);
	}

	public void load(String resource)
	{
		mesh.load(resource);
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();

		mesh.dispose();
		mesh = null;
	}

	@Override
	public PhysBox getPhysbox()
	{
		return mesh.getPhysbox();
	}

	@Override
	public Matrix4f getMatrix()
	{
		return mesh.getMatrix();
	}

	@Override
	public Matrix4f getBoneMatrix(int boneId)
	{
		return mesh.getBoneMatrix(boneId);
	}

}

package zyx.engine.components.animations;

import java.util.ArrayList;
import zyx.utils.interfaces.IUpdateable;

public class MeshAnimator implements IUpdateable
{
	private static final MeshAnimator INSTANCE = new MeshAnimator();
	
	private ArrayList<IAnimateableMesh> meshes;

	public static MeshAnimator getInstance()
	{
		return INSTANCE;
	}
	
	public MeshAnimator()
	{
		meshes = new ArrayList<>();
	}
	
	public void addAnimatedMesh(IAnimateableMesh mesh)
	{
		meshes.add(mesh);
	}
	
	public void removeAnimatedMesh(IAnimateableMesh mesh)
	{
		meshes.remove(mesh);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (IAnimateableMesh mesh : meshes)
		{
			mesh.update(timestamp, elapsedTime);
		}
	}
}

package zyx.utils.cheats;

import java.util.HashMap;
import zyx.opengl.models.implementations.renderers.WorldModelRenderer;
import zyx.utils.interfaces.IPhysbox;

public class DebugPhysics
{

	private static final DebugPhysics INSTANCE = new DebugPhysics();

	private HashMap<IPhysbox, WorldModelRenderer[]> entryMap;

	public DebugPhysics()
	{
		entryMap = new HashMap<>();
	}

	public static DebugPhysics getInstance()
	{
		return INSTANCE;
	}

	public void registerPhysbox(IPhysbox obj)
	{
//		int count = obj.getPhysbox().getTriangles().length;
//		if (count > 0)
//		{
//			WorldModelRenderer[] renderers = DebugPhysDrawing.getRenderersFor(obj);
//			entryMap.put(obj, renderers);
//		}
	}

	public void unregisterPhysbox(IPhysbox obj)
	{
//		WorldModelRenderer[] renderers = entryMap.remove(obj);
//		if (renderers != null)
//		{
//			renderers[DebugPhysDrawing.INDEX_BOUNDING].dispose();
//			renderers[DebugPhysDrawing.INDEX_MESH].dispose();
//		}
//		DebugPhysDrawing.removeModelFor(obj);
	}
}

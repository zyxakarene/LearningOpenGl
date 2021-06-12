package zyx.utils.cheats;

import java.util.HashMap;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.models.implementations.renderers.wrappers.WorldModelWrapper;
import zyx.utils.GameConstants;
import zyx.utils.interfaces.IPhysbox;

public class DebugPhysics
{

	private static final DebugPhysics INSTANCE = new DebugPhysics();

	private HashMap<IPhysbox, WorldModelWrapper[]> entryMap;

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
		if (GameConstants.DRAW_PHYSICS == false)
		{
			return;
		}
		
		int count = obj.getPhysbox().getTriangles().length;
		if (count > 0)
		{
			WorldModelWrapper[] wrappers = DebugPhysDrawing.getWrappersFor(obj);
			entryMap.put(obj, wrappers);
			
			WorldObject parent = obj.getWorldObject();
			for (WorldModelWrapper renderer : wrappers)
			{
				renderer.setup(parent);
			}
		}
	}

	public void unregisterPhysbox(IPhysbox obj)
	{
		if (GameConstants.DRAW_PHYSICS == false)
		{
			return;
		}
		
		WorldModelWrapper[] wrappers = entryMap.remove(obj);
		if (wrappers != null)
		{
			wrappers[DebugPhysDrawing.INDEX_BOUNDING].dispose();
			wrappers[DebugPhysDrawing.INDEX_MESH].dispose();
		}
		DebugPhysDrawing.removeModelFor(obj);
	}
}

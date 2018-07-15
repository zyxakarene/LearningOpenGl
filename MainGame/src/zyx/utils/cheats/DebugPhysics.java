package zyx.utils.cheats;

import java.util.HashMap;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.physics.DebugPhysDrawing;
import zyx.utils.interfaces.IPhysbox;

public class DebugPhysics
{

	private static final DebugPhysics INSTANCE = new DebugPhysics();

	private HashMap<IPhysbox, WorldModel[]> entryMap;

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
		int count = obj.getPhysbox().getTriangles().length;
		if (count > 0)
		{
			WorldModel[] models = DebugPhysDrawing.getModelFor(obj);
			entryMap.put(obj, models);
		}
	}

	public void unregisterPhysbox(IPhysbox obj)
	{
		entryMap.remove(obj);
	}

	public void draw(IPhysbox parent)
	{
		if (entryMap.containsKey(parent))
		{
			GLUtils.setWireframe(true);
			GLUtils.disableCulling();
			WorldModel[] models = entryMap.get(parent);
			
			models[0].draw();
			models[1].draw();
//			for (int i = 0; i < models.length; i++)
//			{
//				WorldModel model = models[i];
//				model.draw();
//			}
			
			GLUtils.enableCulling();
			GLUtils.setWireframe(false);
		}
	}
}

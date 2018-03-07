package zyx.utils.cheats;

import java.util.HashMap;
import zyx.game.components.GameObject;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.physics.DebugPhysDrawing;

public class DebugPhysics
{

	private static final DebugPhysics INSTANCE = new DebugPhysics();

	private HashMap<GameObject, WorldModel> entryMap;

	public DebugPhysics()
	{
		entryMap = new HashMap<>();
	}

	public static DebugPhysics getInstance()
	{
		return INSTANCE;
	}

	public void registerPhysbox(GameObject obj)
	{
		WorldModel model = DebugPhysDrawing.getModelFor(obj.getPhysbox());
		entryMap.put(obj, model);
	}

	public void unregisterPhysbox(GameObject obj)
	{
		entryMap.remove(obj);
	}

	public void draw(GameObject parent)
	{
		if (entryMap.containsKey(parent))
		{
			GLUtils.setWireframe(true);
			entryMap.get(parent).draw();
			GLUtils.setWireframe(false);
		}
	}
}

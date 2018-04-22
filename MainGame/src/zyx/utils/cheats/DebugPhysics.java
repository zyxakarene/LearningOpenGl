package zyx.utils.cheats;

import java.util.HashMap;
import zyx.game.components.GameObject;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.physics.DebugPhysDrawing;

public class DebugPhysics
{

	private static final DebugPhysics INSTANCE = new DebugPhysics();

	private HashMap<GameObject, WorldModel[]> entryMap;

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
		WorldModel[] models = DebugPhysDrawing.getModelFor(obj);
		entryMap.put(obj, models);
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
			GLUtils.disableCulling();
			WorldModel[] models = entryMap.get(parent);
			for (WorldModel model : models)
			{
				model.draw();
			}
			
			GLUtils.enableCulling();
			GLUtils.setWireframe(false);
		}
	}
}

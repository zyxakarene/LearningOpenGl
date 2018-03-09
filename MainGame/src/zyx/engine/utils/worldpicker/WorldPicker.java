package zyx.engine.utils.worldpicker;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.game.components.GameObject;

public class WorldPicker
{
	private ArrayList<GameObject> pickables;
	private Vector3f currentRay;

	public WorldPicker()
	{
		pickables = new ArrayList<>();
		currentRay = RayPicker.getInstance().getRay();
	}
	
	public void addObject(GameObject obj)
	{
		pickables.add(obj);
	}
	
	public void removeObject(GameObject obj)
	{
		pickables.remove(obj);
	}
	
	public void update()
	{
		for (GameObject pickable : pickables)
		{
			
		}
	}
}

package zyx.engine.utils.worldpicker;

import java.util.ArrayList;
import java.util.LinkedList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.utils.worldpicker.calculating.PhysPicker;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.game.components.GameObject;
import zyx.opengl.camera.Camera;
import zyx.utils.FloatMath;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableVector3f;

public class WorldPicker
{
	private ArrayList<GameObject> pickables;
	private Vector3f currentRay;
	private Vector3f currentPos;
	
	private Camera camera;
	
	private ObjectPool<PoolableVector3f> positionPool;
	private LinkedList<PoolableVector3f> collidedPositions;
	private LinkedList<GameObject> collidedObjects;
	private Vector3f outPosition;

	public WorldPicker()
	{
		pickables = new ArrayList<>();
		currentRay = RayPicker.getInstance().getRay();
		currentPos = new Vector3f();
		camera = Camera.getInstance();
		
		positionPool = new ObjectPool<>(PoolableVector3f.class, 10);
		collidedPositions = new LinkedList<>();
		collidedObjects = new LinkedList<>();
		outPosition = new Vector3f();
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
		camera.getPosition(false, currentPos);
		boolean collided;
		PoolableVector3f out;
		
		for (GameObject pickable : pickables)
		{
			collided = PhysPicker.collided(currentPos, currentRay, pickable, outPosition);
			
			if (collided)
			{
				out = positionPool.getInstance();
				out.set(outPosition);
				
				collidedPositions.add(out);
				collidedObjects.add(pickable);
			}
		}
		
		if (collidedPositions.isEmpty() == false)
		{
			handleCollisions();
		}
	}

	private void handleCollisions()
	{
		float closestDistance = Float.MAX_VALUE;
		GameObject closestObject = null;
		Vector3f closestPos = new Vector3f();
		
		PoolableVector3f pos;
		GameObject obj;
		float distance;
		
		while (collidedPositions.isEmpty() == false)
		{
			pos = collidedPositions.removeFirst();
			obj = collidedObjects.removeFirst();
			
			distance = FloatMath.distance(currentPos.x, currentPos.y, currentPos.z, pos.x, pos.y, pos.z);
			if (distance < closestDistance)
			{
				closestDistance = distance;
				closestObject = obj;
				closestPos.set(pos);
			}
			
			positionPool.releaseInstance(pos);
		}
		
		if (closestObject != null)
		{
			ClickedInfo info = new ClickedInfo();
			info.position = closestPos;
			info.target = closestObject;
			
			closestObject.click(info);
		}
	}
}

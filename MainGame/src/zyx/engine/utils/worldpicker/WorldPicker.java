package zyx.engine.utils.worldpicker;

import java.util.ArrayList;
import java.util.LinkedList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.utils.worldpicker.calculating.*;
import zyx.game.components.GameObject;
import zyx.opengl.camera.Camera;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IPhysbox;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableVector3f;

public class WorldPicker
{
	private ArrayList<IPhysbox> pickables;
	private ArrayList<IHoveredItem> clickCallbacks;
	private Vector3f currentRay;
	private Vector3f currentPos;
	
	private Camera camera;
	
	private ObjectPool<PoolableVector3f> positionPool;
	private LinkedList<PoolableVector3f> collidedPositions;
	private LinkedList<IPhysbox> collidedObjects;
	private LinkedList<IHoveredItem> collidedClicks;
	private Vector3f outPosition;
	
	private AbstractPicker pickerImpl;

	public WorldPicker()
	{
		pickables = new ArrayList<>();
		clickCallbacks = new ArrayList<>();
		currentRay = RayPicker.getInstance().getRay();
		currentPos = new Vector3f();
		camera = Camera.getInstance();
		
		positionPool = new ObjectPool<>(PoolableVector3f.class, 10);
		collidedPositions = new LinkedList<>();
		collidedObjects = new LinkedList<>();
		collidedClicks = new LinkedList<>();
		outPosition = new Vector3f();
		
		pickerImpl = new PhysPicker();
	}
	
	public void addObject(IPhysbox obj, IHoveredItem clickCallback)
	{
		pickables.add(obj);
		clickCallbacks.add(clickCallback);
	}
	
	public void removeObject(IPhysbox obj, IHoveredItem clickCallback)
	{
		pickables.remove(obj);
		clickCallbacks.remove(clickCallback);
	}
	
	public void update()
	{
		camera.getPosition(false, currentPos);
		boolean collided;
		PoolableVector3f out;
		IHoveredItem click;
		IPhysbox pickable;
		
		int len = pickables.size();
		for (int i = 0; i < len; i++)
		{
			pickable = pickables.get(i);
			click = clickCallbacks.get(i);
			collided = pickerImpl.collided(currentPos, currentRay, pickable, outPosition);
			
			if (collided)
			{
				out = positionPool.getInstance();
				out.set(outPosition);
				
				collidedClicks.add(click);
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
		IPhysbox closestObject = null;
		IHoveredItem closestClick = null;
		Vector3f closestPos = new Vector3f();
		
		PoolableVector3f pos;
		IPhysbox obj;
		IHoveredItem click;
		float distance;
		
		while (collidedPositions.isEmpty() == false)
		{
			pos = collidedPositions.removeFirst();
			obj = collidedObjects.removeFirst();
			click = collidedClicks.removeFirst();
			
			distance = FloatMath.distance(currentPos.x, currentPos.y, currentPos.z, pos.x, pos.y, pos.z);
			if (distance < closestDistance)
			{
				closestDistance = distance;
				closestObject = obj;
				closestClick = click;
				closestPos.set(pos);
			}
			
			positionPool.releaseInstance(pos);
		}
		
		if (closestObject != null && closestClick != null)
		{
			ClickedInfo info = new ClickedInfo();
			info.position = closestPos;
			info.target = closestObject;
			if (closestObject instanceof GameObject)
			{
				info.gameObject = (GameObject) closestObject;
			}
			else
			{
				info.gameObject = null;
			}
			
			closestClick.onClicked(info);
		}
	}
}

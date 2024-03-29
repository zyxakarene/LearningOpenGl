package zyx.engine.utils.worldpicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.touch.MouseTouchManager;
import zyx.engine.utils.worldpicker.calculating.*;
import zyx.opengl.camera.Camera;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPhysbox;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableVector3f;

public class WorldPicker implements IDisposeable
{
	private static final int MAX_DISTANCE = 5000;

	private ArrayList<PickEntity> pickables;
	private HashMap<IPhysbox, PickEntity> pickableMap;
	private RayPickerRay currentRay;
	private Vector3f currentPos;

	private Camera camera;
	private MouseTouchManager mouseTouchManager;

	private ObjectPool<PoolableVector3f> positionPool;
	private LinkedList<PoolableVector3f> collidedPositions;
	private LinkedList<PickEntity> collidedObjects;
	private Vector3f outPosition;

	private AbstractPicker pickerImpl;

	public WorldPicker()
	{
		pickables = new ArrayList<>();
		pickableMap = new HashMap<>();
		currentPos = new Vector3f();

		currentRay = RayPicker.getInstance().getRay();
		mouseTouchManager = MouseTouchManager.getInstance();
		camera = Camera.getInstance();

		positionPool = new ObjectPool<>(PoolableVector3f.class, 10);
		collidedPositions = new LinkedList<>();
		collidedObjects = new LinkedList<>();
		outPosition = new Vector3f();

		pickerImpl = new PhysPicker();

	}

	public void addObject(IPhysbox obj, IWorldPickedItem clickCallback)
	{
		addObject(obj, clickCallback, MAX_DISTANCE);
	}
	
	public void addObject(IPhysbox obj, IWorldPickedItem clickCallback, int maxDistance)
	{
		PickEntity entity = pickableMap.get(obj);
		if (entity == null)
		{
			entity = new PickEntity(obj);
			pickables.add(entity);
			pickableMap.put(obj, entity);
		}

		entity.add(clickCallback, maxDistance);
	}

	public void removeObject(IPhysbox obj, IWorldPickedItem clickCallback)
	{
		PickEntity entity = pickableMap.get(obj);
		if (entity != null)
		{
			entity.remove(clickCallback);

			if (entity.isEmpty())
			{
				pickables.remove(entity);
				pickableMap.remove(obj);
			}
		}
	}

	public void update()
	{
		if (!currentRay.valid || mouseTouchManager.hasTarget())
		{
			//Mouse over a UI component, so never hit world geometry
			return;
		}

		camera.getPosition(false, currentPos);

		for (PickEntity entity : pickables)
		{
			boolean collided = pickerImpl.collided(currentPos, currentRay, entity.target, outPosition);

			if (collided)
			{
				PoolableVector3f hitPos = positionPool.getInstance();
				hitPos.set(outPosition);

				collidedPositions.add(hitPos);
				collidedObjects.add(entity);
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
		PickEntity closestObject = null;
		Vector3f closestPos = new Vector3f();

		while (collidedPositions.isEmpty() == false)
		{
			PoolableVector3f pos = collidedPositions.removeFirst();
			PickEntity entity = collidedObjects.removeFirst();

			float distance = FloatMath.distance(currentPos.x, currentPos.y, currentPos.z, pos.x, pos.y, pos.z);
			if (distance < closestDistance)
			{
				closestDistance = distance;
				closestObject = entity;
				closestPos.set(pos);
			}

			positionPool.releaseInstance(pos);
		}

		if (closestObject != null)
		{
			ClickedInfo info = new ClickedInfo();
			info.position = closestPos;
			info.target = closestObject.target;
			info.distance = closestDistance;
			info.worldObject = closestObject.targetAsWorldObject();
			
			closestObject.onGeometryPicked(info);
		}
	}

	@Override
	public void dispose()
	{
		if (pickables != null)
		{
			for (PickEntity entity : pickables)
			{
				entity.dispose();
			}
			
			pickables.clear();
			pickables = null;
		}
		
		pickableMap.clear();
		collidedPositions.clear();
		collidedObjects.clear();

		positionPool.dispose();

		pickableMap = null;
		collidedPositions = null;
		collidedObjects = null;
		pickerImpl = null;
	}
}

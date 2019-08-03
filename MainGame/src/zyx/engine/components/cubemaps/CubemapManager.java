package zyx.engine.components.cubemaps;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.CubemapResource;
import zyx.opengl.reflections.Cubemap;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IUpdateable;
import zyx.utils.pooling.GenericPool;
import zyx.utils.pooling.ObjectPool;

public class CubemapManager implements IResourceReady<CubemapResource>, IUpdateable
{
	private static final Vector3f HELPER_VECTOR = new Vector3f();
	private static final CubemapManager INSTANCE = new CubemapManager();

	public static CubemapManager getInstance()
	{
		return INSTANCE;
	}

	private CubemapResource resource;

	public ObjectPool<CubemapEntry> entryPool;
	private Vector3f[] positions;
	private ArrayList<IReflective> itemsList;
	private HashMap<IReflective, CubemapEntry> entryMap;
	
	public CubemapManager()
	{
		positions = new Vector3f[0];
		itemsList = new ArrayList<>();
		entryMap = new HashMap<>();
		entryPool = new GenericPool<>(CubemapEntry.class, 10);
	}
	
	public void load(String id)
	{
		resource = ResourceManager.getInstance().<CubemapResource>getResourceAs(id);
		resource.registerAndLoad(this);
	}
	
	public void addItem(IReflective object)
	{
		CubemapEntry entry = entryMap.get(object);
		if (entry == null)
		{
			entry = entryPool.getInstance();
			entry.object = object;
			
			entryMap.put(object, entry);
			itemsList.add(object);
		}
	}
	
	public void removeItem(IReflective object)
	{
		CubemapEntry entry = entryMap.get(object);
		if (entry != null)
		{
			entryMap.remove(object);
			entryPool.releaseInstance(entry);
			
			itemsList.remove(object);
		}
	}
	
	public void dirtyPosition(IReflective object)
	{
		CubemapEntry entry = entryMap.get(object);
		if (entry != null)
		{
			entry.dirty = true;
		}
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (IReflective item : itemsList)
		{
			CubemapEntry entry = entryMap.get(item);
			
			if (entry.dirty)
			{
				entry.dirty = false;
				updateCubemapIndexFor(entry.object);
			}
		}
	}
	
	private void updateCubemapIndexFor(IReflective object)
	{
		object.getPosition(false, HELPER_VECTOR);
		Vector3f position;
		
		int index = 0;
		float shortestDistance = Float.MAX_VALUE;
		for (int i = 0; i < positions.length; i++)
		{
			position = positions[i];
			
			float distance = FloatMath.distance(position, HELPER_VECTOR);
			if (distance < shortestDistance)
			{
				index = i;
				shortestDistance = distance;
			}
		}

		object.setCubemapIndex(index);
	}
	
	@Override
	public void onResourceReady(CubemapResource resource)
	{
		Cubemap content = resource.getContent();
		content.bind();
		
		positions = content.positions;
	}

	public void clean()
	{
		if (resource != null)
		{
			resource.unregister(this);
			resource = null;
		}
		
		for (IReflective item : itemsList)
		{
			CubemapEntry entry = entryMap.get(item);
			entryPool.releaseInstance(entry);
		}
		
		itemsList.clear();
		entryMap.clear();
	}
}

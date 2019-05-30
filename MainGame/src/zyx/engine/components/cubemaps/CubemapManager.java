package zyx.engine.components.cubemaps;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.CubemapResource;
import zyx.opengl.reflections.Cubemap;
import zyx.utils.interfaces.IUpdateable;
import zyx.utils.pooling.GenericPool;
import zyx.utils.pooling.ObjectPool;

public class CubemapManager implements IResourceReady<CubemapResource>, IUpdateable
{
	private static final CubemapManager instance = new CubemapManager();

	public static CubemapManager getInstance()
	{
		return instance;
	}
	public ObjectPool<CubemapEntry> entryPool = new GenericPool<>(CubemapEntry.class, 10);

	private CubemapResource resource;
	
	private Vector3f[] positions;
	private ArrayList<WorldObject> itemsList;
	private HashMap<WorldObject, CubemapEntry> entryMap;
	
	public CubemapManager()
	{
		itemsList = new ArrayList<>();
		entryMap = new HashMap<>();
	}
	
	public void load(String id)
	{
		CubemapResource res = ResourceManager.getInstance().<CubemapResource>getResourceAs(id);
		res.registerAndLoad(this);
	}
	
	public void addItem(WorldObject object)
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
	
	public void removeItem(WorldObject object)
	{
		CubemapEntry entry = entryMap.get(object);
		if (entry != null)
		{
			entryMap.remove(object);
			entryPool.releaseInstance(entry);
			
			itemsList.remove(object);
		}
	}
	
	public void dirtyPosition(WorldObject object)
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
		for (WorldObject item : itemsList)
		{
			CubemapEntry entry = entryMap.get(item);
		}
	}
	
	private void updatePosFor(WorldObject item)
	{
		
	}
	
	@Override
	public void onResourceReady(CubemapResource resource)
	{
		this.resource = resource;
		
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
		
		for (WorldObject item : itemsList)
		{
			CubemapEntry entry = entryMap.get(item);
			entryPool.releaseInstance(entry);
		}
		
		itemsList.clear();
		entryMap.clear();
	}
}

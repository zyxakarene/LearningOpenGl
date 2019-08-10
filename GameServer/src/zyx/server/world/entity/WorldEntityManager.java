package zyx.server.world.entity;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class WorldEntityManager<T extends WorldEntity>
{

	protected HashMap<Integer, T> entitiesById;
	protected ArrayList<T> entities;

	protected WorldEntityManager()
	{
		entitiesById = new HashMap<>();
		entities = new ArrayList<>();
	}

	protected void addEntity(T entity)
	{
		entities.add(entity);
		entitiesById.put(entity.id, entity);
	}

	public void removeEntity(int id)
	{
		T entity = entitiesById.get(id);

		if (entity != null)
		{
			removeEntity(entity);
		}
	}

	public void removeEntity(T entity)
	{
		entities.remove(entity);
		entitiesById.remove(entity.id);
	}

	public ArrayList<T> getAllEntities()
	{
		return entities;
	}

	public T getEntity(int id)
	{
		return entitiesById.get(id);
	}
}

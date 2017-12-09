package zyx.game.components.world;

import java.util.ArrayList;
import zyx.game.components.WorldObject;
import zyx.utils.interfaces.IDrawable;

public final class Scene implements IDrawable
{

	public ArrayList<WorldObject> objects;

	public Scene()
	{
		objects = new ArrayList<>();
	}

	public void addChild(WorldObject child)
	{
		objects.add(child);
	}

	public void removeChild(WorldObject child)
	{
		objects.remove(child);
	}

	@Override
	public void draw()
	{
		int len = objects.size();
		for (int i = 0; i < len; i++)
		{
			objects.get(i).draw();
		}
	}

}

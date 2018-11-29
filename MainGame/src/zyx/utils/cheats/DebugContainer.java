package zyx.utils.cheats;

import java.util.ArrayList;
import java.util.LinkedList;
import zyx.engine.components.world.WorldObject;
import zyx.game.components.GameObject;
import zyx.opengl.shaders.implementations.Shader;

public class DebugContainer extends WorldObject
{

	private static final DebugContainer INSTANCE = new DebugContainer();

	private ArrayList<DebugPoint> points;
	private LinkedList<DebugPoint> removedPoints;

	public DebugContainer()
	{
		super(Shader.WORLD);
		points = new ArrayList<>();
		removedPoints = new LinkedList<>();
	}

	public static DebugContainer getInstance()
	{
		return INSTANCE;
	}

	public void addPoint(DebugPoint point)
	{
		addChild(point);
		points.add(point);
	}

	public void clear()
	{
		for (DebugPoint point : points)
		{
			point.kill();
		}
	}
	
	public void update(long timestamp, int elapsedTime)
	{
		DebugPoint point;
		while (!removedPoints.isEmpty())
		{
			point = removedPoints.removeLast();
			point.dispose();
		}

		for (int i = points.size() - 1; i >= 0; i--)
		{
			point = points.get(i);
			point.update(timestamp, elapsedTime);

			if (point.isAlive() == false)
			{
				removedPoints.add(point);
				points.remove(i);

				removeChild(point);
			}
		}
	}
}

package zyx.utils;

import java.util.ArrayList;
import zyx.utils.math.Vector2Int;
import zyx.utils.pooling.ObjectPool;

public class Bresenham2D
{
	private static final ObjectPool<Vector2Int> POOL = new ObjectPool<>(Vector2Int.class, 10, new Object[0]);
	
	private static final ArrayList<Vector2Int> RESULT = new ArrayList<>();
	
	public static ArrayList<Vector2Int> getLineBetween(int x0, int y0, int x1, int y1)
	{
		for (Vector2Int point : RESULT)
		{
			POOL.releaseInstance(point);
		}
		RESULT.clear();
		
		Vector2Int point;
		boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
		if (steep)
		{
			int temp = x0;
			x0 = y0;
			y0 = temp;

			temp = x1;
			x1 = y1;
			y1 = temp;
		}

		if (x0 > x1)
		{
			int x0B = x0;
			int y0B = y0;

			int x1B = x1;
			int y1B = y1;

			x1 = x0B;
			x0 = x1B;
			y1 = y0B;
			y0 = y1B;
		}

		int deltaX = x1 - x0;
		int deltaY = Math.abs(y1 - y0);
		int error = deltaX / 2;
		int yStep = 0;
		int y = y0;

		if (y0 < y1)
		{
			yStep = 1;
		}
		else
		{
			yStep = -1;
		}

		for (int x = x0; x <= x1; x++)
		{
			point = POOL.getInstance();
			if (steep)
			{
				point.x = y;
				point.y = x;
			}
			else
			{
				point.x = x;
				point.y = y;
			}
			RESULT.add(point);
			
			error = error - deltaY;
			if (error < 0)
			{
				y = y + yStep;
				error = error + deltaX;
			}
		}

		return RESULT;
	}
}

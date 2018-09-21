package zyx.utils;

import java.util.*;

public class ListUtils
{
	public static float[] toFloatArray(List<Float> list)
	{
		int len = list.size();
		float[] result = new float[len];
		
		if (list instanceof RandomAccess)
		{
			for (int i = 0; i < len; i++)
			{
				result[i] = list.get(i);
			}
		}
		else
		{
			Iterator<Float> iterator = list.iterator();
			for (int i = 0; i < len; i++)
			{
				result[i] = iterator.next();
			}
		}
		
		return result;
	}
	
	public static int[] toIntArray(List<Integer> list)
	{
		int len = list.size();
		int[] result = new int[len];
		
		if (list instanceof RandomAccess)
		{
			for (int i = 0; i < len; i++)
			{
				result[i] = list.get(i);
			}
		}
		else
		{
			Iterator<Integer> iterator = list.iterator();
			for (int i = 0; i < len; i++)
			{
				result[i] = iterator.next();
			}
		}
		
		return result;
	}
}

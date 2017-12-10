package zyx.opengl.textures.bitmapfont;

import java.util.List;

class FontUtils
{
	static float[] toFloatArray(List<Float> list)
	{
		int len = list.size();
		float[] result = new float[len];
		
		for (int i = 0; i < len; i++)
		{
			result[i] = list.get(i);
		}
		
		return result;
	}
	
	static int[] toIntArray(List<Integer> list)
	{
		int len = list.size();
		int[] result = new int[len];
		
		for (int i = 0; i < len; i++)
		{
			result[i] = list.get(i);
		}
		
		return result;
	}
}

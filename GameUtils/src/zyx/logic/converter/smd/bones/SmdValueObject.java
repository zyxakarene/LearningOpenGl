package zyx.logic.converter.smd.bones;

import java.util.Collection;
import java.util.LinkedList;

public class SmdValueObject
{
	private final LinkedList<Float> vertexData = new LinkedList<>();
	private final LinkedList<Integer> elementData = new LinkedList<>();
	
	public void addVertexData(Collection<? extends Float> newData)
	{
		vertexData.addAll(newData);
		
		elementData.add(elementData.size());
		for (int i = 0; i < newData.size(); i++)
		{
		}
	}
	
	public float[] getVertexData()
	{
		return toFloatArray(vertexData);
	}

	public int[] getElementData()
	{
		return toIntArray(elementData);
	}
	
	private float[] toFloatArray(LinkedList<Float> list)
    {
        float[] data = new float[list.size()];
        for (int i = 0; i < data.length; i++)
        {
            data[i] = list.removeFirst();
        }

        return data;
    }

    private int[] toIntArray(LinkedList<Integer> list)
    {
        int[] data = new int[list.size()];
        for (int i = 0; i < data.length; i++)
        {
            data[i] = list.removeFirst();
        }

        return data;
    }
}

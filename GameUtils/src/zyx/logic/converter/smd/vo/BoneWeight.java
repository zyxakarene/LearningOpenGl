package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;

public class BoneWeight
{
	private int MAX_AMOUNT = 4;
	
	private byte[] indexes = new byte[MAX_AMOUNT];
	private float[] weights = new float[MAX_AMOUNT];
	private int pointer = 0;
	
	public BoneWeight()
	{
		indexes[0] = 0;
		indexes[1] = 0;
		
		weights[0] = 0f;
		weights[1] = 0f;
	}

	public void addWeight(byte index, float weight)
	{
		if (pointer >= MAX_AMOUNT)
		{
			throw new RuntimeException("Too many weights added!");
		}
		
		indexes[pointer] = index;
		weights[pointer] = weight;
		
		pointer++;
	}

	public void save(DataOutputStream out) throws IOException
	{
		for (int i = 0; i < pointer; i++)
		{
			out.writeFloat(indexes[i]);
		}
		for (int i = 0; i < pointer; i++)
		{
			out.writeFloat(weights[i]);
		}
	}
}

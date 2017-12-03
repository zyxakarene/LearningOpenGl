package zyx.opengl.models.loading.bones;

import java.io.DataInputStream;
import java.io.IOException;

class SmdObject
{
	SmdBone rootBone;
	float[] triangleData;
	int[] elementData;
	SmdAnimation[] animations;
	
	public void read(DataInputStream in) throws IOException
	{
		rootBone = new SmdBone();
		rootBone.read(in);
		
		int triangleCount = in.readInt();
		triangleData = new float[triangleCount * 3 * 12];
		System.out.println(triangleCount + " triangles");
		System.out.println(triangleData.length + " floats");
		for (int i = 0; i < triangleData.length; i++)
		{
			triangleData[i] = in.readFloat();
		}

		elementData = new int[triangleCount * 3];
		System.out.println(elementData.length + " elements");
		for (int i = 0; i < elementData.length; i++)
		{
			elementData[i] = i;
		}
		
		
		int animationLength = in.readInt();
		animations = new SmdAnimation[animationLength];
		for (int i = 0; i < animationLength; i++)
		{
			animations[i] = new SmdAnimation();
			animations[i].read(in);
		}
	}
}
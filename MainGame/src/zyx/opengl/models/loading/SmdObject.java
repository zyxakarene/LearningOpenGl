package zyx.opengl.models.loading;

import java.io.DataInputStream;
import java.io.IOException;
import zyx.utils.cheats.Print;

class SmdObject
{
	SmdBone rootBone;
	float[] vertexData;
	int[] elementData;
	SmdAnimation[] animations;
	SmdPhysInfo physInformation;
	String texture;
	
	public void read(DataInputStream in) throws IOException
	{
		Print.out("Parsing model data from byte count:", in.available());
		
		rootBone = new SmdBone();
		rootBone.read(in);

		int vertexCount = in.readInt();
		vertexData = new float[vertexCount * 12];
		Print.out("↳", vertexCount, "verticies");
		Print.out("↳", vertexData.length, "floats");
		for (int i = 0; i < vertexData.length; i++)
		{
			vertexData[i] = in.readFloat();
		}
		
		int elementCount = in.readInt();
		elementData = new int[elementCount];
		Print.out("↳", elementData.length, "elements");
		for (int i = 0; i < elementData.length; i++)
		{
			elementData[i] = in.readShort();
		}
		
		int animationLength = in.readInt();
		animations = new SmdAnimation[animationLength];
		Print.out("↳", elementData.length, "animations");
		for (int i = 0; i < animationLength; i++)
		{
			animations[i] = new SmdAnimation();
			animations[i].read(in);
		}
		
		physInformation = new SmdPhysInfo();
		physInformation.read(in);
		
		Print.out("↳", physInformation.physBoxes.length, "physboxes\n");
		
		texture = in.readUTF();
	}
}

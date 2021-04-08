package zyx.opengl.models.implementations.bones.skeleton;

import org.lwjgl.util.vector.Matrix4f;

public class BoneProvider
{
	private static final int BONE_COUNT = 30;

	public static final Matrix4f[] BONES = new Matrix4f[BONE_COUNT];
	public static final Matrix4f[] INVERT_BONES = new Matrix4f[BONE_COUNT];
	
	private static int[] hashes = new int[BONE_COUNT];
	private static boolean[] changes = new boolean[BONE_COUNT];

	static
	{
		for (int i = 0; i < BONES.length; i++)
		{
			BONES[i] = new Matrix4f();
			INVERT_BONES[i] = new Matrix4f();
			
			hashes[i] = -1;
			changes[i] = true;
		}
	}

	static void BoneChanged(int id)
	{
		changes[id] = true;
	}
	
	public static int GetTotalHash()
	{
		int sum = 0;
		
		for (int i = 0; i < BONE_COUNT; i++)
		{
			if (changes[i])
			{
				changes[i] = false;
				updateHash(i);
			}
			
			sum += hashes[i];
			
		}
		
		return sum;
	}

	private static void updateHash(int index)
	{
		Matrix4f bone = BONES[index];
		
		float hash = 3f;
		hash += (bone.m00 * 11);
		hash += (bone.m01 * 12);
		hash += (bone.m02 * 13);
		hash += (bone.m03 * 14);
		hash += (bone.m10 * 15);
		hash += (bone.m11 * 16);
		hash += (bone.m12 * 17);
		hash += (bone.m13 * 18);
		hash += (bone.m20 * 19);
		hash += (bone.m21 * 21);
		hash += (bone.m22 * 22);
		hash += (bone.m23 * 23);
		hash += (bone.m30 * 24);
		hash += (bone.m31 * 25);
		hash += (bone.m32 * 26);
		hash += (bone.m33 * 27);
		
		hashes[index] = (int)hash;
	}
	
	
	
}

package zyx.opengl.models.implementations.bones.skeleton;

import org.lwjgl.util.vector.Matrix4f;

public class BoneProvider
{

	public static final Matrix4f[] BONES = new Matrix4f[30];
	public static final Matrix4f[] INVERT_BONES = new Matrix4f[30];

	static
	{
		for (int i = 0; i < BONES.length; i++)
		{
			BONES[i] = new Matrix4f();
			INVERT_BONES[i] = new Matrix4f();
		}

		Joint.setBones(BONES, INVERT_BONES);
	}
}

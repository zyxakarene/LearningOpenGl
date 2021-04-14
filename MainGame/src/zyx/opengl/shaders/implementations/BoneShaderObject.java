package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.engine.GameEngine;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.bones.skeleton.BoneProvider;
import zyx.opengl.shaders.source.ShaderReplacement;

class BoneShaderObject
{

	private final Matrix4f[] BONES = BoneProvider.BONES;
	private final Matrix4f[] INVERT_BONES = BoneProvider.INVERT_BONES;

	private int boneMatrixTrans;
	private int boneMatrixTrans_InverseTranspose;
	private int lastBoneHash;

	public final int boneCount;

	BoneShaderObject(int boneCount)
	{
		this.boneCount = boneCount;

		if (boneCount < 1 || boneCount > 4)
		{
			throw new RuntimeException("ERROR: BoneCount must be between 1 and 4");
		}
	}

	public void postLoading(int program)
	{
		boneMatrixTrans = -1;
		boneMatrixTrans = UniformUtils.createUniform(program, "bones");
		boneMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "bonesInverseTranspose");
	}

	public void uploadBones()
	{
		synchronized (BONES)
		{
			int hash = BoneProvider.GetTotalHash();
			if (lastBoneHash != hash)
			{
				GameEngine.boneUploads++;
				lastBoneHash = hash;
				UniformUtils.setUniformMatrix(boneMatrixTrans, BONES);
				UniformUtils.setUniformMatrix(boneMatrixTrans_InverseTranspose, INVERT_BONES);
			}
		}
	}

	public ShaderReplacement[] getVertexReplacements()
	{
		String boneString;
		if (boneCount == 1)
		{
			boneString = "float";
		}
		else
		{
			boneString = "vec" + boneCount;
		}

		return new ShaderReplacement[]
		{
			new ShaderReplacement("%BoneCount%", boneString)
		};
	}
}

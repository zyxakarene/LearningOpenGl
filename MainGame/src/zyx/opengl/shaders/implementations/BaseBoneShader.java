package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.engine.GameEngine;
import zyx.opengl.models.implementations.bones.skeleton.BoneProvider;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.source.ShaderReplacement;

public abstract class BaseBoneShader extends AbstractShader
{

	public final Matrix4f[] BONES = BoneProvider.BONES;
	public final Matrix4f[] INVERT_BONES = BoneProvider.INVERT_BONES;

	private int boneMatrixTrans;
	private int boneMatrixTrans_InverseTranspose;
	private int lastBoneHash;

	protected final int boneCount;
	
	public BaseBoneShader(Object lock, int boneCount)
	{
		super(lock);
		this.boneCount = boneCount;
		
		if (boneCount < 1 || boneCount > 4)
		{
			throw new RuntimeException("ERROR: BoneCount must be between 1 and 4");
		}
	}

	@Override
	protected final void postLoading()
	{
		boneMatrixTrans = UniformUtils.createUniform(program, "bones");
		boneMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "bonesInverseTranspose");
		
		onPostLoading();
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
	
	abstract protected void onPostLoading();

	@Override
	protected ShaderReplacement[] getVertexReplacements()
	{
		String boneString;
		if (boneCount == 1)
		{
			boneString = "float";
		}
		else
		{
			boneString = "vec"+boneCount;
		}
		
		return new ShaderReplacement[]
		{
			new ShaderReplacement("%BoneCount%", boneString)
		};
	}
}

package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.implementations.bones.skeleton.BoneProvider;
import zyx.opengl.shaders.AbstractShader;

public abstract class BaseBoneShader extends AbstractShader
{

	public final Matrix4f[] BONES = BoneProvider.BONES;
	public final Matrix4f[] INVERT_BONES = BoneProvider.INVERT_BONES;

	private int boneMatrixTrans;
	private int boneMatrixTrans_InverseTranspose;

	public BaseBoneShader(Object lock)
	{
		super(lock);
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
			UniformUtils.setUniformMatrix(boneMatrixTrans, BONES);
			UniformUtils.setUniformMatrix(boneMatrixTrans_InverseTranspose, INVERT_BONES);
		}
	}
	
	abstract protected void onPostLoading();

}

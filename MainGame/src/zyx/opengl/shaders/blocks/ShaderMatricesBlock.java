package zyx.opengl.shaders.blocks;

import zyx.opengl.shaders.AbstractShaderBlock;
import zyx.opengl.shaders.SharedShaderObjects;

public class ShaderMatricesBlock extends AbstractShaderBlock
{

	public static final String KEY = "SharedMatrices";
	
	private int projIndex;
	private int viewIndex;
	private int projViewIndex;
	
	public ShaderMatricesBlock(Object lock)
	{
		super(lock);
	}

	@Override
	protected void addComponents()
	{
		projIndex = addMatrix(4, 4);
		viewIndex = addMatrix(4, 4);
		projViewIndex = addMatrix(4, 4);
	}

	@Override
	protected void onUpload()
	{
		setData(projIndex, SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
		setData(viewIndex, SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM);
		setData(projViewIndex, SharedShaderObjects.WORLD_PROJECTION_VIEW_TRANSFORM);
	}

	@Override
	public String GetName()
	{
		return KEY;
	}
}

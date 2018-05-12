package zyx.opengl.models;

import zyx.opengl.GLUtils;
import zyx.opengl.shaders.implementations.Shader;

public abstract class AbstractInstancedModel extends AbstractModel
{

	private int instanceVbo;
	
	private int instanceCount;

	public AbstractInstancedModel(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void createObjects()
	{
		super.createObjects();
		instanceVbo = ModelUtils.generateBufferObject();
	}

	protected void setInstanceData(float[] data, int count)
	{
		ModelUtils.bindBufferObject_Array(instanceVbo);
		ModelUtils.fillVBO_Dynamic(data);
		instanceCount = count;
	}
	
	protected final void addInstanceAttribute(String attributeName, int components, int stride, int offset)
	{
		ModelUtils.bindBufferObject_Array(instanceVbo);
		ModelUtils.addInstanceAttribute(meshShader.program, attributeName, components, stride, offset);
	}
	
	@Override
	public void draw()
	{
		meshShader.bind();

		if (texture != null)
		{
			texture.bind();
			ModelUtils.drawInstancedElements(vao, elementCount, instanceCount);
		}
	}

	@Override
	public void dispose()
	{
		ModelUtils.disposeBuffer(instanceVbo);
		super.dispose();
	}

}
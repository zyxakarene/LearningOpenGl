package zyx.opengl.models;

import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;

public abstract class AbstractModel implements IDrawable, IDisposeable
{

	private final AbstractShader meshShader;
	
	private final int vao;
	private final int vbo;
	private final int ebo;

	private int elementCount;

	public AbstractModel(Shader shader)
	{
		meshShader = ShaderManager.INSTANCE.get(shader);
		
		vao = ModelUtils.generateVertexArray();
		ModelUtils.bindVertexArray(vao);

		vbo = ModelUtils.generateBufferObject();
		ModelUtils.bindBufferObject_Array(vbo);

		ebo = ModelUtils.generateBufferObject();
		ModelUtils.bindBufferObject_Element(ebo);

		setupAttributes();
	}

	protected void setVertexData(float[] vertexData, int[] elementData)
	{
		ModelUtils.fillVBO_Static(vertexData);
		ModelUtils.fillEBO_Static(elementData);

		this.elementCount = elementData.length;
	}

	@Override
	public void draw()
	{
		ModelUtils.drawElements(vao, elementCount);
	}

	protected final void addAttribute(String attributeName, int components, int stride, int offset)
	{
		ModelUtils.addAttribute(meshShader.program, attributeName, components, stride, offset);
	}

	protected abstract void setupAttributes();

	@Override
	public void dispose()
	{
		ModelUtils.disposeModel(vao, vbo, ebo);
	}
}

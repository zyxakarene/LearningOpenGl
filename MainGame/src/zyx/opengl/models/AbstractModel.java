package zyx.opengl.models;

import java.nio.FloatBuffer;
import zyx.opengl.materials.Material;
import zyx.opengl.models.implementations.renderers.MeshRenderer;
import zyx.opengl.shaders.AbstractShader;
import zyx.utils.interfaces.IDisposeable;

public abstract class AbstractModel<TMaterial extends Material> implements IDisposeable
{

	protected final AbstractShader meshShader;

	protected int vao;
	protected int vbo;
	protected int ebo;

	protected int elementCount;
	
	protected TMaterial defaultMaterial;

	public AbstractModel(TMaterial defaultMaterial)
	{
		this.meshShader = defaultMaterial.shader;
		this.defaultMaterial = defaultMaterial;
	}

	protected final void setup()
	{
		createObjects();
		setupAttributes();
	}
	
	protected void createObjects()
	{
		vao = ModelUtils.generateVertexArray();
		ModelUtils.bindVertexArray(vao);
		
		vbo = ModelUtils.generateBufferObject();
		ModelUtils.bindBufferObject_Array(vbo);

		ebo = ModelUtils.generateBufferObject();
		ModelUtils.bindBufferObject_Element(ebo);
	}

	protected void bindVao()
	{
		ModelUtils.bindVertexArray(vao);
		ModelUtils.bindBufferObject_Array(vbo);
		ModelUtils.bindBufferObject_Element(ebo);
	}

	protected void setVertexData(float[] vertexData, int[] elementData)
	{
		ModelUtils.bindBufferObject_Array(vbo);

		ModelUtils.fillVBO_Static(vertexData);
		ModelUtils.fillEBO_Static(elementData);

		this.elementCount = elementData.length;
	}

	protected void updateVertexSubData(FloatBuffer buffer, long offset)
	{
		ModelUtils.bindBufferObject_Array(vbo);
		ModelUtils.setVBOSub(buffer, offset);
	}

	public TMaterial getClonedMaterial()
	{
		return (TMaterial) defaultMaterial.cloneMaterial();
	}
	
	public abstract MeshRenderer createRenderer();
	
	public final void draw()
	{
		draw(defaultMaterial);
	}
	
	public void draw(TMaterial material)
	{
		if (elementCount > 0)
		{
			if (canDraw())
			{
				material.bind();
				
				ModelUtils.drawElements(vao, elementCount);
			}
		}
	}

	protected final void addAttribute(String attributeName, int components, int stride, int offset)
	{
		ModelUtils.bindBufferObject_Array(vbo);
		ModelUtils.addAttribute(meshShader.program, attributeName, components, stride, offset);
	}

	protected abstract void setupAttributes();

	@Override
	public void dispose()
	{
		ModelUtils.disposeBuffer(vbo);
		ModelUtils.disposeBuffer(ebo);
		ModelUtils.disposeVertexArray(vao);
	}

	protected boolean canDraw()
	{
		return true;
	}
	
}

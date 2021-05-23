package zyx.opengl.models;

import java.nio.FloatBuffer;
import zyx.opengl.materials.Material;
import zyx.opengl.shaders.AbstractShader;

public class ModelData<TMaterial extends Material>
{
	public final AbstractShader meshShader;
	public final TMaterial defaultMaterial;

	protected int vao;
	protected int vbo;
	protected int ebo;

	protected int elementCount;
	
	public ModelData(TMaterial material)
	{
		this.meshShader = material.shader;
		this.defaultMaterial = material;
	}

	void createObjects()
	{
		vao = ModelUtils.generateVertexArray();
		ModelUtils.bindVertexArray(vao);
		
		vbo = ModelUtils.generateBufferObject();
		ModelUtils.bindBufferObject_Array(vbo);

		ebo = ModelUtils.generateBufferObject();
		ModelUtils.bindBufferObject_Element(ebo);
	}

	void bindVao()
	{
		ModelUtils.bindVertexArray(vao);
	}

	void setVertexData(float[] vertexData, int[] elementData)
	{
		ModelUtils.bindBufferObject_Array(vbo);

		ModelUtils.fillVBO_Static(vertexData);
		ModelUtils.fillEBO_Static(elementData);

		this.elementCount = elementData.length;
	}

	void updateVertexSubData(FloatBuffer buffer, long offset)
	{
		ModelUtils.bindBufferObject_Array(vbo);
		ModelUtils.setVBOSub(buffer, offset);
	}

	void dispose()
	{
		ModelUtils.disposeBuffer(vbo);
		ModelUtils.disposeBuffer(ebo);
		ModelUtils.disposeVertexArray(vao);
	}

	void addAttribute(String attributeName, int components, int stride, int offset)
	{
		ModelUtils.bindBufferObject_Array(vbo);
		ModelUtils.addAttribute(meshShader.program, attributeName, components, stride, offset);
	}
}

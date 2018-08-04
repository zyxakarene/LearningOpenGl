package zyx.opengl.models;

import java.nio.FloatBuffer;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;

public abstract class AbstractModel implements IDrawable, IDisposeable
{

	protected final AbstractShader meshShader;

	protected int vao;
	protected int vbo;
	protected int ebo;

	protected int elementCount;

	protected AbstractTexture texture;
	protected AbstractTexture overwriteTexture;

	public AbstractModel(Shader shader)
	{
		meshShader = ShaderManager.INSTANCE.get(shader);
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

	public void setOverwriteTexture(AbstractTexture texture)
	{
		this.overwriteTexture = texture;
	}
	
	protected void setTexture(AbstractTexture texture)
	{
		this.texture = texture;
	}

	public AbstractTexture getTexture()
	{
		return texture;
	}

	@Override
	public void draw()
	{
		if (elementCount > 0)
		{
			meshShader.bind();

			if (overwriteTexture != null)
			{
				overwriteTexture.bind();
			}
			else if (texture != null)
			{
				texture.bind();
			}
			
			ModelUtils.drawElements(vao, elementCount);
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
		
		texture = null;
	}

}

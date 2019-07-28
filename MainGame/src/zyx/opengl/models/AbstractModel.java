package zyx.opengl.models;

import java.nio.FloatBuffer;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;

public abstract class AbstractModel implements IDrawable, IDisposeable
{

	private static final AbstractTexture[] NO_TEXTURES = new AbstractTexture[0];

	protected final AbstractShader meshShader;

	protected int vao;
	protected int vbo;
	protected int ebo;

	protected int elementCount;

	private AbstractTexture[] textures = NO_TEXTURES;

	public AbstractModel(Shader shader)
	{
		meshShader = ShaderManager.getInstance().get(shader);
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

	protected void setTexture(AbstractTexture texture)
	{
		textures = new AbstractTexture[]
		{
			texture
		};
	}

	protected void setTextures(AbstractTexture[] texture)
	{
		textures = texture;
	}

	public AbstractTexture getDefaultTexture()
	{
		return textures[0];
	}

	public AbstractTexture[] getTextures()
	{
		return textures;
	}

	@Override
	public void draw()
	{
		if (elementCount > 0)
		{
			if (canDraw())
			{
				bindTextures();

				ModelUtils.drawElements(vao, elementCount);
			}
		}
	}

	protected void bindTextures()
	{
		for (AbstractTexture texture : textures)
		{
			texture.bind();
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

		textures = null;
	}

	protected boolean canDraw()
	{
		return true;
	}

}

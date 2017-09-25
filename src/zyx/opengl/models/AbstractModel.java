package zyx.opengl.models;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.GameTexture;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;

public abstract class AbstractModel implements IDrawable, IDisposeable
{

	protected static final Vector3f SHARED_ROTATION = new Vector3f(0, 0, 0);
	protected static final Vector3f SHARED_POSITION = new Vector3f(0, 0, 0);
	protected static final Vector3f SHARED_SCALE = new Vector3f(1, 1, 1);

	protected final AbstractShader meshShader;

	private final int vao;
	private final int vbo;
	private final int ebo;

	private int elementCount;

	private GameTexture texture;

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

	protected void setTexture(GameTexture texture)
	{
		this.texture = texture;
	}

	@Override
	public void draw()
	{
		meshShader.bind();

		if (texture != null)
		{
			texture.bind();
		}

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

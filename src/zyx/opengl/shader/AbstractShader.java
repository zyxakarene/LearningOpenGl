package zyx.opengl.shader;

import zyx.opengl.shader.source.ShaderSourceLoader;

public abstract class AbstractShader
{

	protected int vertexShader, fragmentShader;
	
	public int program;

	static final Object LOCK = new Object();

	public AbstractShader(Object lock)
	{
		if (lock != LOCK)
		{
			throw new RuntimeException();
		}
	}

	final void load()
	{
		String vertexSource = ShaderSourceLoader.getSource(getVertexName());
		String fragmentSource = ShaderSourceLoader.getSource(getFragmentName());

		vertexShader = ShaderUtils.createVertexShader(vertexSource);
		fragmentShader = ShaderUtils.createFragmentShader(fragmentSource);

		program = ShaderUtils.createProgram(vertexShader, fragmentShader);

		postLoading();
	}

	final void activate()
	{
		ShaderUtils.useShader(program);
	}

	protected abstract void postLoading();

	protected abstract String getVertexName();

	protected abstract String getFragmentName();
}

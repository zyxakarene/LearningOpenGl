package zyx.opengl.shaders;

import zyx.opengl.shaders.source.ShaderSourceLoader;
import zyx.utils.interfaces.IUpdateable;

public abstract class AbstractShader implements IUpdateable
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

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}

	public final void bind()
	{
		ShaderBinder.bindShader(this);
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
	
	public abstract String getName();
	
	public abstract void upload();
	
	protected abstract void postLoading();

	protected abstract String getVertexName();

	protected abstract String getFragmentName();

}

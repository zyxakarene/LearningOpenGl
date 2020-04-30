package zyx.opengl.shaders;

import zyx.opengl.GLUtils;
import zyx.opengl.shaders.source.ShaderReplacement;
import zyx.opengl.shaders.source.ShaderSourceLoader;
import zyx.utils.interfaces.IUpdateable;

public abstract class AbstractShader implements IUpdateable
{
	private static final ShaderReplacement[] NO_REPLACEMENTS = new ShaderReplacement[0];

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
		String vertexSource = ShaderSourceLoader.getSource(getVertexName(), getVertexReplacements());
		String fragmentSource = ShaderSourceLoader.getSource(getFragmentName(), getFragmentReplacements());

		vertexShader = ShaderUtils.createVertexShader(vertexSource);
		fragmentShader = ShaderUtils.createFragmentShader(fragmentSource);

		program = ShaderUtils.createProgram(vertexShader, fragmentShader);
		GLUtils.errorCheck();
		
		bind();
		GLUtils.errorCheck();
		
		postLoading();
		GLUtils.errorCheck();
	}
	
	public abstract String getName();
	
	public abstract void upload();
	
	protected abstract void postLoading();

	protected abstract String getVertexName();

	protected abstract String getFragmentName();

	protected ShaderReplacement[] getVertexReplacements()
	{
		return NO_REPLACEMENTS;
	}

	protected ShaderReplacement[] getFragmentReplacements()
	{
		return NO_REPLACEMENTS;
	}

}

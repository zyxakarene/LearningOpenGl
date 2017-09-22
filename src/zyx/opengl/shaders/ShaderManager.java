package zyx.opengl.shaders;

import java.util.HashMap;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.interfaces.IUpdateable;

public class ShaderManager implements IUpdateable
{

	public static final ShaderManager INSTANCE = new ShaderManager();

	private final HashMap<Shader, AbstractShader> shaderMap;
	private final AbstractShader[] shaderArray;
	
	private final Shader[] shaders;

	private ShaderManager()
	{
		shaders = Shader.values();
		shaderMap = new HashMap<>();
		shaderArray = new AbstractShader[shaders.length];
	}

	public void initialize()
	{
		shaderMap.put(Shader.WORLD, new WorldShader(AbstractShader.LOCK));
		
		int length = shaders.length;
		for (int i = 0; i < length; i++)
		{
			shaderMap.get(shaders[i]).load();
		}
	}
	
	public void bind(Shader shader)
	{
		shaderMap.get(shader).bind();
	}
	
	public AbstractShader get(Shader shader)
	{
		return shaderMap.get(shader);
	}

	@Override
	public void update(int elapsedTime)
	{
		int len = shaderArray.length;
		for (int i = 0; i < len; i++)
		{
			shaderArray[i].update(elapsedTime);
		}
	}
}

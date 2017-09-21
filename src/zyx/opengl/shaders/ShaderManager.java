package zyx.opengl.shaders;

import java.util.HashMap;
import zyx.opengl.shaders.implementations.BaseShader;
import zyx.opengl.shaders.implementations.Shader;

public class ShaderManager
{

	public static final ShaderManager INSTANCE = new ShaderManager();

	private final HashMap<Shader, AbstractShader> shaderMap;

	private ShaderManager()
	{
		shaderMap = new HashMap<>();
	}

	public void initialize()
	{
		shaderMap.put(Shader.BASE, new BaseShader(AbstractShader.LOCK));
		
		Shader[] shaders = Shader.values();
		int length = shaders.length;
		for (int i = 0; i < length; i++)
		{
			shaderMap.get(shaders[i]).load();
		}
	}
	
	public void activate(Shader shader)
	{
		shaderMap.get(shader).activate();
	}
	
	public AbstractShader get(Shader shader)
	{
		return shaderMap.get(shader);
	}
}

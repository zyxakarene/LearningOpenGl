package zyx.opengl.shaders;

class ShaderBinder
{

	private static AbstractShader currentShader;

	static void bindShader(AbstractShader shader)
	{
		if (currentShader != shader)
		{
			currentShader = shader;
			ShaderUtils.useShader(currentShader.program);
		}
	}
}

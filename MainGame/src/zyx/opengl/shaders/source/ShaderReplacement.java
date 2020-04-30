package zyx.opengl.shaders.source;

import java.util.regex.Pattern;

public class ShaderReplacement
{
	Pattern pattern;
	String replacement;

	public ShaderReplacement(String regex, String replacement)
	{
		this.pattern = Pattern.compile(regex);
		this.replacement = replacement;
	}
}

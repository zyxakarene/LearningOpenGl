package zyx.opengl.shaders.source;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import zyx.utils.cheats.Print;
import zyx.utils.exceptions.Msg;

public class ShaderSourceLoader
{

	private static final Pattern INCLUDE_PATTERN = Pattern.compile("#include \"(\\w+\\.glsl)\";?");

	public static String getSource(String name)
	{
		String shader = getFileContent(name);
		boolean testingIncludes;

		do
		{
			testingIncludes = false;
			Matcher regexMatch = INCLUDE_PATTERN.matcher(shader);

			while (regexMatch.find())
			{
				String wholeMatch = regexMatch.group(0);
				String libraryName = regexMatch.group(1);
				
				String libraryContent = getFileContent(libraryName);
				
				if (libraryContent.isEmpty())
				{
					Print.err("[Warning] Adding empty GLSL library file to " + name);
				}
				shader = shader.replace(wholeMatch, libraryContent);
				
				testingIncludes = true;
			}

		}
		while (testingIncludes);

		return shader;
	}

	private static String getFileContent(String name)
	{
		try (InputStream shaderStream = ShaderSourceLoader.class.getResourceAsStream(name))
		{
			int read;
			byte[] readBuffer = new byte[256];
			StringBuilder builder = new StringBuilder();
			while ((read = shaderStream.read(readBuffer)) > 0)
			{
				builder.append(new String(readBuffer, 0, read));
			}

			return builder.toString();
		}
		catch (Exception ex)
		{
			String errorReason = "Failed read shader file [" + name + "]";
			Print.err(errorReason);
			return "";
		}
	}
}

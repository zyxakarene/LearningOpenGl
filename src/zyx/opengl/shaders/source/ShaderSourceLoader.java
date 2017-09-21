package zyx.opengl.shaders.source;

import java.io.IOException;
import java.io.InputStream;
import zyx.utils.exceptions.Msg;

public class ShaderSourceLoader
{

	public static String getSource(String name)
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
		catch (IOException ex)
		{
			String errorReason = "Failed to compile shader " + name;
			Msg.error(errorReason, ex);
			throw new RuntimeException(errorReason);
		}
	}
}

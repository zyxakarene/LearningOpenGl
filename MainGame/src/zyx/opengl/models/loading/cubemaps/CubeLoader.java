package zyx.opengl.models.loading.cubemaps;

import zyx.opengl.reflections.*;
import java.io.IOException;
import java.util.logging.Level;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.utils.GameConstants;
import zyx.utils.PrintBuilder;
import zyx.utils.cheats.Print;

public class CubeLoader
{

	public static LoadableCubemapVO loadFromCube(ResourceDataInputStream in, String id)
	{
		PrintBuilder builder = new PrintBuilder();

		try
		{
			builder.append("==== Parsing cube data from byte count:", in.available(), "====");
			builder.append("Id", id);

			CubemapObject vo = new CubemapObject();
			vo.read(in, builder);

			builder.append("========");
			Print.out(builder);

			return new LoadableCubemapVO(vo.name, vo.faceSize, vo.layers, vo.internalFormat, vo.format, vo.type, vo.textureData, vo.positions);
		}
		catch (IOException e)
		{
			builder.append("==== [ERROR] Failed to parse mesh! ====");
			Print.out(builder);

			GameConstants.LOGGER.log(Level.SEVERE, "Error at loading a cube data", e);
			return null;
		}
	}
}

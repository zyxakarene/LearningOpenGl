package zyx.opengl.models.loading.cubemaps;

import zyx.opengl.reflections.*;
import java.io.IOException;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.loading.cubemaps.fallback.FakeCubemap;
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
			builder.append("==== [ERROR] Failed to parse cubemap! ====");
			Print.err(builder);

			return FakeCubemap.makeFakeCube();
		}
	}
}

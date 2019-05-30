package zyx.opengl.reflections;

import java.io.IOException;
import java.util.logging.Level;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.CubemapArrayTexture;
import zyx.utils.GameConstants;

public class CubeLoader
{
	public static Cubemap loadFromCube(ResourceDataInputStream in)
	{
		try
		{
			LoadableCubemapVO cubeVo = new LoadableCubemapVO();
			cubeVo.read(in);
			
			CubemapArrayTexture texture = new CubemapArrayTexture(cubeVo);

			return new Cubemap(cubeVo.positions, texture);
		}
		catch (IOException e)
		{
			GameConstants.LOGGER.log(Level.SEVERE, "Error at loading a cube data", e);
			return null;
		}
	}
}

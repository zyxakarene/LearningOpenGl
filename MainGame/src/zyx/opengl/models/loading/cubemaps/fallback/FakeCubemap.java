package zyx.opengl.models.loading.cubemaps.fallback;

import java.io.ByteArrayOutputStream;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.reflections.LoadableCubemapVO;

public class FakeCubemap
{

	private static final int FACES_PER_LAYER = 6;
	private static final int FACE_SIZE = 8;
	private static final int LAYERS = 1;
	private static final int BYTES_PER_PIXEL = 3;

	public static LoadableCubemapVO makeFakeCube()
	{
		String name = "cube.fake";
		short faceSize = FACE_SIZE;
		short layers = LAYERS;
		int internalFormat = GL11.GL_RGB;
		int format = GL11.GL_RGB;
		int type = GL11.GL_BYTE;

		byte[] data = getByteData();

		Vector3f[] positions = new Vector3f[]
		{
			new Vector3f(0, 0, 0)
		};

		return new LoadableCubemapVO(name, faceSize, layers, internalFormat, format, type, data, positions);
	}

	private static byte[] getByteData()
	{
		int pixelCount = FACE_SIZE * FACE_SIZE * LAYERS * FACES_PER_LAYER * BYTES_PER_PIXEL;
		ByteArrayOutputStream byteData = new ByteArrayOutputStream(pixelCount);
		boolean isBlack = true;
		
		for (int faceCount = 0; faceCount < FACES_PER_LAYER; faceCount++)
		{
			for (int layerCount = 0; layerCount < LAYERS; layerCount++)
			{
				for (int w = 0; w < FACE_SIZE; w++)
				{
					for (int h = 0; h < FACE_SIZE; h++)
					{
						if (h == 0)
						{
							isBlack = !isBlack;
						}
						
						if (isBlack)
						{
							byteData.write(-127);
							byteData.write(-127);
							byteData.write(-127);
						}
						else
						{
							byteData.write(127);
							byteData.write(-127);
							byteData.write(127);
						}
						
						isBlack = !isBlack;
					}
				}
			}
		}
		
		return byteData.toByteArray();
	}
}

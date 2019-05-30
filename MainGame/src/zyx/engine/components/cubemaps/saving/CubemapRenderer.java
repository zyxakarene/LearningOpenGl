package zyx.engine.components.cubemaps.saving;

import java.io.*;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;

public class CubemapRenderer implements ICubemapRenderer
{

	private static final int GL_INTERNAL_FORMAT = GL11.GL_RGB;
	private static final int GL_FORMAT = GL11.GL_RGB;
	private static final int GL_TYPE = GL11.GL_BYTE;
	
	private static final int BYTES_PER_PIXEL = 3;

	private final String name;

	private ByteArrayOutputStream byteData;
	private short faceSize;
	private short cubeCount;
	private Vector3f[] positions;

	CubemapRenderer(String name, Vector3f[] positions)
	{
		this.name = name;
		this.positions = positions;
		faceSize = GameConstants.GAME_WIDTH;
		cubeCount = (short) positions.length;
		

		int facePerCube = 6;

		int pixelCount = faceSize * faceSize * cubeCount * facePerCube * BYTES_PER_PIXEL;
		byteData = new ByteArrayOutputStream(pixelCount);
	}

	void writeToFile()
	{
		File file = new File("assets/cubemaps/" + name);

		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file)))
		{
			out.writeUTF(name);
			
			out.writeShort(faceSize);
			out.writeShort(cubeCount);
			
			out.writeInt(GL_INTERNAL_FORMAT);
			out.writeInt(GL_FORMAT);
			out.writeInt(GL_TYPE);
			
			byte[] pixelData = byteData.toByteArray();
			out.writeInt(pixelData.length);
			out.write(pixelData);
			
			for (Vector3f position : positions)
			{
				out.writeFloat(position.x);
				out.writeFloat(position.y);
				out.writeFloat(position.z);
			}
			
			out.flush();
		}
		catch (IOException ex)
		{
			Print.err(ex.getLocalizedMessage());
		}

	}

	@Override
	public void renderCubemap()
	{
		ByteBuffer bb = BufferUtils.createByteBuffer(faceSize * faceSize * BYTES_PER_PIXEL);
		GL11.glReadPixels(0, 0, 128, 128, GL_INTERNAL_FORMAT, GL_TYPE, bb);

		while (bb.hasRemaining())
		{
			byte b = bb.get();
			byteData.write(b);
		}

		DeferredRenderer.getInstance().setCubemapRenderer(null);
	}

}

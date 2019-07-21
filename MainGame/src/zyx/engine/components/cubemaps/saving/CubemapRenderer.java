package zyx.engine.components.cubemaps.saving;

import java.io.*;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.utils.cheats.Print;

public class CubemapRenderer implements ICubemapRenderer
{

	public static final int FACE_SIZE = 128;
	
	private static final int GL_INTERNAL_FORMAT = GL11.GL_RGB;
	private static final int GL_FORMAT = GL11.GL_RGB;
	private static final int GL_TYPE = GL11.GL_BYTE;
	
	private static final int BYTES_PER_PIXEL = 3;
	
	private final String name;

	private ByteArrayOutputStream byteData;
	private short cubeCount;
	private Vector3f[] positions;

	CubemapRenderer(String name, Vector3f[] positions)
	{
		this.name = name;
		this.positions = positions;
		cubeCount = (short) positions.length;
		

		int facePerCube = 6;

		int pixelCount = FACE_SIZE * FACE_SIZE * cubeCount * facePerCube * BYTES_PER_PIXEL;
		byteData = new ByteArrayOutputStream(pixelCount);
	}

	void writeToFile()
	{
		File file = new File("assets/cubemaps/" + name);

		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file)))
		{
			out.writeUTF(name);
			
			out.writeShort(FACE_SIZE);
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
		ByteBuffer bb = BufferUtils.createByteBuffer(FACE_SIZE * FACE_SIZE * BYTES_PER_PIXEL);
		GL11.glReadPixels(0, 0, FACE_SIZE, FACE_SIZE, GL_INTERNAL_FORMAT, GL_TYPE, bb);

		while (bb.hasRemaining())
		{
			byte b = bb.get();
			byteData.write(b);
		}

		DeferredRenderer.getInstance().setCubemapRenderer(null);
	}

}

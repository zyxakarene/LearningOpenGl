package zyx.engine.components.cubemaps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.utils.cheats.Print;

public class CubemapRenderer implements ICubemapRenderer
{
	
	private FileOutputStream wr;
	
	CubemapRenderer(String name)
	{
		try
		{
			File file = new File("assets/cubemaps/" + name);
			wr = new FileOutputStream(file);
		}
		catch (FileNotFoundException ex)
		{
			Print.err(ex.getLocalizedMessage());
		}
	}
	
	void finalizeCubemap()
	{
		try
		{
			wr.flush();
			wr.close();
		}
		catch (IOException ex)
		{
			Print.err(ex.getLocalizedMessage());
		}
	}
	
	@Override
	public void renderCubemap()
	{
		ByteBuffer bb = BufferUtils.createByteBuffer(128 * 128 * 3);
		GL11.glReadPixels(0, 0, 128, 128, GL11.GL_RGB, GL11.GL_BYTE, bb);
		
		try
		{
			while (bb.hasRemaining())
			{
				byte b = bb.get();
				wr.write(b);
			}

			wr.flush();
		}
		catch (IOException ex)
		{
			Print.err(ex.getLocalizedMessage());
		}

		DeferredRenderer.getInstance().setCubemapRenderer(null);
	}

}

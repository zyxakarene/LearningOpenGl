package zyx.opengl.buffers;

import org.lwjgl.opengl.GL11;
import zyx.opengl.textures.FrameBufferTexture;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.opengl.textures.enums.TextureFormat;
import zyx.utils.interfaces.IShadowable;

public class DepthRenderer extends BaseFrameBuffer
{

	private static final DepthRenderer INSTANCE = new DepthRenderer();

	private FrameBufferTexture depthBuffer;

	public static DepthRenderer getInstance()
	{
		return INSTANCE;
	}

	public DepthRenderer()
	{
		super(Buffer.DEPTH, 2f);
	}

	@Override
	protected void onCreateFrameBufferTextures()
	{
		depthBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_0, TextureFormat.FORMAT_1_CHANNEL_16F);
	}

	public void drawShadowable(IShadowable shadowable)
	{
		bindBuffer();
		
		GL11.glViewport(0, 0, w, h);
		shadowable.drawShadow();
		GL11.glViewport(0, 0, (int) (w / renderScale), (int) (h / renderScale));
	}

	@Override
	protected int[] getAttachments()
	{
		return new int[]
		{
			depthBuffer.attachment
		};
	}

	public int depthInt()
	{
		return depthBuffer.id;
	}

	@Override
	protected void onDispose()
	{
		if (depthBuffer != null)
		{
			depthBuffer.dispose();
			depthBuffer = null;
		}
	}
}

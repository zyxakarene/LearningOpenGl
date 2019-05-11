package zyx.opengl.buffers;

import zyx.opengl.textures.FrameBufferTexture;
import zyx.opengl.textures.enums.TextureAttachment;

public class DepthRenderer extends BaseFrameBuffer
{

	private static DepthRenderer instance = new DepthRenderer();

	private FrameBufferTexture depthBuffer;

	public static DepthRenderer getInstance()
	{
		return instance;
	}

	public DepthRenderer()
	{
		super(Buffer.DEPTH);
	}

	@Override
	protected void onCreateFrameBufferTextures()
	{
		depthBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_3);
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
}

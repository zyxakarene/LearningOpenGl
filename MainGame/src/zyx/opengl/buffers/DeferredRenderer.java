package zyx.opengl.buffers;

import zyx.opengl.textures.FrameBufferTexture;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.opengl.textures.enums.TextureFormat;

public class DeferredRenderer extends BaseFrameBuffer
{

	private static final DeferredRenderer INSTANCE = new DeferredRenderer();

	private FrameBufferTexture positionBuffer;
	private FrameBufferTexture normalBuffer;
	private FrameBufferTexture colorSpecBuffer;
	private FrameBufferTexture depthBuffer;
	private FrameBufferTexture screenPositionBuffer;
	private FrameBufferTexture screenNormalBuffer;
	private FrameBufferTexture cubeIndexBuffer;

	public static DeferredRenderer getInstance()
	{
		return INSTANCE;
	}

	public DeferredRenderer()
	{
		super(Buffer.DEFERRED, 1f);
	}

	@Override
	protected void onCreateFrameBufferTextures()
	{
		positionBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_0, TextureFormat.FORMAT_3_CHANNEL_16F);
		normalBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_1, TextureFormat.FORMAT_3_CHANNEL_16F);
		colorSpecBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_2, TextureFormat.FORMAT_4_CHANNEL_UBYTE);
		depthBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_3, TextureFormat.FORMAT_1_CHANNEL_16F);
		screenPositionBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_4, TextureFormat.FORMAT_3_CHANNEL_16F);
		screenNormalBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_5, TextureFormat.FORMAT_3_CHANNEL_16F);
		cubeIndexBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_6, TextureFormat.FORMAT_1_CHANNEL_UBYTE);
	}

	@Override
	protected int[] getAttachments()
	{
		return new int[]
		{
			positionBuffer.attachment,
			normalBuffer.attachment,
			colorSpecBuffer.attachment,
			depthBuffer.attachment,
			screenPositionBuffer.attachment,
			screenNormalBuffer.attachment,
			cubeIndexBuffer.attachment
		};
	}
	
	public int positionInt()
	{
		return positionBuffer.id;
	}

	public int normalInt()
	{
		return normalBuffer.id;
	}

	public int colorInt()
	{
		return colorSpecBuffer.id;
	}

	public int depthInt()
	{
		return depthBuffer.id;
	}

	public int screenPositionInt()
	{
		return screenPositionBuffer.id;
	}

	public int screenNormalInt()
	{
		return screenNormalBuffer.id;
	}

	public int cubeIndexInt()
	{
		return cubeIndexBuffer.id;
	}

	@Override
	protected void onDispose()
	{
		if (positionBuffer != null)
		{
			positionBuffer.dispose();
			normalBuffer.dispose();
			colorSpecBuffer.dispose();
			depthBuffer.dispose();
			screenPositionBuffer.dispose();
			screenNormalBuffer.dispose();
			cubeIndexBuffer.dispose();
			
			positionBuffer = null;
			normalBuffer = null;
			colorSpecBuffer = null;
			depthBuffer = null;
			screenPositionBuffer = null;
			screenNormalBuffer = null;
			cubeIndexBuffer = null;
		}
	}
}

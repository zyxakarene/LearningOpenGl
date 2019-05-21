package zyx.opengl.buffers;

import org.lwjgl.opengl.GL11;
import zyx.opengl.textures.FrameBufferTexture;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.utils.GameConstants;
import zyx.utils.interfaces.IShadowable;

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
		super(Buffer.DEPTH, 2f);
	}

	@Override
	protected void onCreateFrameBufferTextures()
	{
		depthBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_0);
	}

	public void drawShadowable(IShadowable shadowable)
	{
		bindBuffer();
		
		GL11.glViewport(0, 0, w, h);
		shadowable.drawShadow();
		GL11.glViewport(0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
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

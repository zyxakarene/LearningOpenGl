package zyx.opengl.buffers;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import zyx.opengl.models.implementations.DeferredLightModel;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.FrameBufferTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.opengl.textures.enums.TextureSlot;

public class DeferredRenderer extends BaseFrameBuffer
{

	private static DeferredRenderer instance = new DeferredRenderer();

	private FrameBufferTexture positionBuffer;
	private FrameBufferTexture normalBuffer;
	private FrameBufferTexture colorBuffer;

	private TextureFromInt positionTexture;
	private TextureFromInt normalTexture;
	private TextureFromInt colorTexture;
	private TextureFromInt depthTexture;

	private DeferredLightModel model;

	public static DeferredRenderer getInstance()
	{
		return instance;
	}

	public DeferredRenderer()
	{
		super(Buffer.DEFERRED, 1f);

	}

	@Override
	protected void onCreateFrameBufferTextures()
	{
		positionBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_0);
		normalBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_1);
		colorBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_2);
	}

	@Override
	protected void onBufferCreated()
	{
		int depthInt = DepthRenderer.getInstance().depthInt();
		
		positionTexture = new TextureFromInt(w, h, positionBuffer.id, TextureSlot.SLOT_0);
		normalTexture = new TextureFromInt(w, h, normalBuffer.id, TextureSlot.SLOT_1);
		colorTexture = new TextureFromInt(w, h, colorBuffer.id, TextureSlot.SLOT_2);
		depthTexture = new TextureFromInt(w, h, depthInt, TextureSlot.SLOT_3);
		
		model = new DeferredLightModel(positionTexture, normalTexture, colorTexture, depthTexture);
	}

	public void draw()
	{
		BufferBinder.bindBuffer(Buffer.DEFAULT);
		ShaderManager.INSTANCE.bind(Shader.DEFERED_LIGHT_PASS);

		model.draw();

		glBindFramebuffer(GL_READ_FRAMEBUFFER, depthBufferId);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0); // write to default framebuffer
		glBlitFramebuffer(0, 0, w, h, 0, 0, w, h, GL_DEPTH_BUFFER_BIT, GL_NEAREST);
	}

	@Override
	protected int[] getAttachments()
	{
		return new int[]
		{
			positionBuffer.attachment, normalBuffer.attachment, colorBuffer.attachment
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
		return colorBuffer.id;
	}
}

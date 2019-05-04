package zyx.opengl.deferred;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import zyx.opengl.models.implementations.DeferredLightModel;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.BufferBinder;
import zyx.opengl.textures.FrameBufferTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.GameConstants;

public class DeferredRenderer
{

	private static DeferredRenderer instance = new DeferredRenderer();

	private final int bufferId;

	private FrameBufferTexture positionBuffer;
	private FrameBufferTexture normalBuffer;
	private FrameBufferTexture colorBuffer;
	
	private TextureFromInt positionTexture;
	private TextureFromInt normalTexture;
	private TextureFromInt colorTexture;
	
	private DeferredLightModel model;

	public static DeferredRenderer getInstance()
	{
		return instance;
	}

	public DeferredRenderer()
	{
		int w = GameConstants.GAME_WIDTH;
		int h = GameConstants.GAME_HEIGHT;

		bufferId = GL30.glGenFramebuffers();
		BufferBinder.bindBuffer(bufferId);

		positionBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_0);
		normalBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_1);
		colorBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_2);

		setupBufferValues(w, h);

		BufferBinder.bindBuffer(0);

		positionTexture = new TextureFromInt(w, h, positionBuffer.id, TextureSlot.SLOT_0);
		normalTexture = new TextureFromInt(w, h, normalBuffer.id, TextureSlot.SLOT_1);
		colorTexture = new TextureFromInt(w, h, colorBuffer.id, TextureSlot.SLOT_2);
		
		model = new DeferredLightModel(positionTexture, normalTexture, colorTexture);
	}

	public void draw()
	{
		BufferBinder.bindBuffer(0);
		ShaderManager.INSTANCE.bind(Shader.DEFERED_LIGHT_PASS);
		model.draw();
	}

	public void prepareRender()
	{
		BufferBinder.bindBuffer(bufferId);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
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
	
	private void setupBufferValues(int width, int height)
	{
		int attachments[] =
		{
			positionBuffer.attachment, normalBuffer.attachment, colorBuffer.attachment
		};

		IntBuffer buffer = BufferUtils.createIntBuffer(attachments.length);
		buffer.put(attachments);
		buffer.flip();

		GL20.glDrawBuffers(buffer);

		int rboDepth = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rboDepth);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboDepth);

		// finally check if framebuffer is complete
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			System.out.println("Framebuffer not complete!");
			System.exit(-1);
		}
	}
}

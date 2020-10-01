package zyx.opengl.buffers;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import zyx.engine.components.cubemaps.saving.ICubemapRenderer;
import zyx.opengl.materials.impl.DeferredMaterial;
import zyx.opengl.models.implementations.FullScreenQuadModel;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.FrameBufferTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.opengl.textures.enums.TextureFormat;
import zyx.opengl.textures.enums.TextureSlot;

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

	private TextureFromInt positionTexture;
	private TextureFromInt normalTexture;
	private TextureFromInt colorTexture;
	private TextureFromInt depthTexture;
	private TextureFromInt shadowDepthTexture;
	private TextureFromInt ambientOcclusionTexture;
	private TextureFromInt cubeIndexTexture;

	private FullScreenQuadModel model;
	
	private ICubemapRenderer cubemapRenderer;

	public static DeferredRenderer getInstance()
	{
		return INSTANCE;
	}
	private DeferredMaterial material;

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
	void onBuffersCreated()
	{
		int depthInt = DepthRenderer.getInstance().depthInt();
		int ambientInt = AmbientOcclusionRenderer.getInstance().ambientOcclusionInt();

		positionTexture = new TextureFromInt(w, h, positionBuffer.id, TextureSlot.DEFERRED_POSITION);
		normalTexture = new TextureFromInt(w, h, normalBuffer.id, TextureSlot.DEFERRED_NORMAL);
		colorTexture = new TextureFromInt(w, h, colorSpecBuffer.id, TextureSlot.DEFERRED_COLOR_SPEC);
		depthTexture = new TextureFromInt(w, h, depthBuffer.id, TextureSlot.DEFERRED_DEPTH);
		shadowDepthTexture = new TextureFromInt(w, h, depthInt, TextureSlot.DEFERRED_SHADOW);
		ambientOcclusionTexture = new TextureFromInt(w, h, ambientInt, TextureSlot.DEFERRED_AO);
		cubeIndexTexture = new TextureFromInt(w, h, cubeIndexBuffer.id, TextureSlot.DEFERRED_CUBE_INDEX);

		material = new DeferredMaterial(Shader.DEFERED_LIGHT_PASS);
		material.setPosition(positionTexture);
		material.setNormal(normalTexture);
		material.setColorSpec(colorTexture);
		material.setDepth(depthTexture);
		material.setShadow(shadowDepthTexture);
		material.setAmbientOcclusion(ambientOcclusionTexture);
		material.setCubeIndex(cubeIndexTexture);
		
		model = new FullScreenQuadModel(material);
	}

	public void draw()
	{
		BufferBinder.bindBuffer(Buffer.DEFAULT);
		ShaderManager.getInstance().bind(Shader.DEFERED_LIGHT_PASS);
		ShaderManager.getInstance().get(Shader.DEFERED_LIGHT_PASS).upload();

		model.draw();
		
		if (cubemapRenderer != null)
		{
			cubemapRenderer.renderCubemap();
		}
		
		int readBufferId = depthBufferId;
		int writeBufferId = Buffer.DEFAULT.bufferId;

		glBindFramebuffer(GL_READ_FRAMEBUFFER, readBufferId);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, writeBufferId); // write to default framebuffer
		glBlitFramebuffer(0, 0, w, h, 0, 0, w, h, GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT, GL_NEAREST);
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

	public void setCubemapRenderer(ICubemapRenderer cubemapRenderer)
	{
		this.cubemapRenderer = cubemapRenderer;
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
		
		if (model != null)
		{
			model.dispose();
			model = null;
		}
	}
}

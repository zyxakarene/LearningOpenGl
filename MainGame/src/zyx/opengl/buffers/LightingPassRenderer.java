package zyx.opengl.buffers;

import org.lwjgl.opengl.GL11;
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

public class LightingPassRenderer extends BaseFrameBuffer
{

	private static final LightingPassRenderer INSTANCE = new LightingPassRenderer();

	private FrameBufferTexture outputBuffer;

	private TextureFromInt positionTexture;
	private TextureFromInt normalTexture;
	private TextureFromInt colorTexture;
	private TextureFromInt depthTexture;
	private TextureFromInt shadowDepthTexture;
	private TextureFromInt ambientOcclusionTexture;
	private TextureFromInt cubeIndexTexture;

	private FullScreenQuadModel model;
	
	private ICubemapRenderer cubemapRenderer;

	public static LightingPassRenderer getInstance()
	{
		return INSTANCE;
	}
	private DeferredMaterial material;

	public LightingPassRenderer()
	{
		super(Buffer.LIGHTING_PASS, 1f);
		doClearColor = false;
	}

	@Override
	protected void onCreateFrameBufferTextures()
	{
		outputBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_0, TextureFormat.FORMAT_3_CHANNEL_UBYTE);
	}

	@Override
	void onBuffersCreated()
	{
		DeferredRenderer renderer = DeferredRenderer.getInstance();
		int depthInt = DepthRenderer.getInstance().depthInt();
		int ambientInt = AmbientOcclusionRenderer.getInstance().ambientOcclusionInt();

		positionTexture = new TextureFromInt(w, h, renderer.positionInt(), TextureSlot.DEFERRED_POSITION);
		normalTexture = new TextureFromInt(w, h, renderer.normalInt(), TextureSlot.DEFERRED_NORMAL);
		colorTexture = new TextureFromInt(w, h, renderer.colorInt(), TextureSlot.DEFERRED_COLOR_SPEC);
		depthTexture = new TextureFromInt(w, h, renderer.depthInt(), TextureSlot.DEFERRED_DEPTH);
		shadowDepthTexture = new TextureFromInt(w, h, depthInt, TextureSlot.DEFERRED_SHADOW);
		ambientOcclusionTexture = new TextureFromInt(w, h, ambientInt, TextureSlot.DEFERRED_AO);
		cubeIndexTexture = new TextureFromInt(w, h, renderer.cubeIndexInt(), TextureSlot.DEFERRED_CUBE_INDEX);

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
		BufferBinder.bindBuffer(Buffer.LIGHTING_PASS);
		ShaderManager.getInstance().bind(Shader.DEFERED_LIGHT_PASS);
		ShaderManager.getInstance().get(Shader.DEFERED_LIGHT_PASS).upload();

		model.draw();
		
		if (cubemapRenderer != null)
		{
			cubemapRenderer.renderCubemap();
		}
		
		DeferredRenderer renderer = DeferredRenderer.getInstance();
		int readBufferId = renderer.depthBufferId;
		int writeBufferId = bufferId;

		glBindFramebuffer(GL_READ_FRAMEBUFFER, readBufferId);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, writeBufferId);
		glBlitFramebuffer(0, 0, w, h, 0, 0, w, h, GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT, GL_NEAREST);
	}

	@Override
	protected int[] getAttachments()
	{
		return new int[]
		{
			outputBuffer.attachment
		};
	}

	public void setCubemapRenderer(ICubemapRenderer cubemapRenderer)
	{
		this.cubemapRenderer = cubemapRenderer;
	}

	public int outputInt()
	{
		return outputBuffer.id;
	}

	@Override
	protected void onDispose()
	{
		if (outputBuffer != null)
		{
			outputBuffer.dispose();
			outputBuffer = null;
		}
		
		if (model != null)
		{
			model.dispose();
			model = null;
		}
	}
}

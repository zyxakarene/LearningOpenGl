package zyx.opengl.buffers;

import zyx.opengl.models.implementations.FullScreenQuadModel;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.FrameBufferTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.opengl.textures.enums.TextureSlot;

public class AmbientOcclusionRenderer extends BaseFrameBuffer
{

	private static AmbientOcclusionRenderer instance = new AmbientOcclusionRenderer();

	private FrameBufferTexture ambientOcclusionBuffer;
	
	private TextureFromInt normalTexture;
	private TextureFromInt positionTexture;
	
	private FullScreenQuadModel model;

	public static AmbientOcclusionRenderer getInstance()
	{
		return instance;
	}

	public AmbientOcclusionRenderer()
	{
		super(Buffer.AMBIENT_OCCLUSION, 1f);
	}

	@Override
	protected void onCreateFrameBufferTextures()
	{
		ambientOcclusionBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_0);
	}

	@Override
	protected void onBufferCreated()
	{
		int positionTextureId = DeferredRenderer.getInstance().positionInt();
		positionTexture = new TextureFromInt(w, h, positionTextureId, TextureSlot.SLOT_0);

		int normalTextureId = DeferredRenderer.getInstance().normalInt();
		normalTexture = new TextureFromInt(w, h, normalTextureId, TextureSlot.SLOT_1);
		
		model = new FullScreenQuadModel(Shader.AMBIENT_OCCLUSION, positionTexture, normalTexture);
	}
	
	public void drawAmbientOcclusion()
	{
		bindBuffer();
		ShaderManager.getInstance().bind(Shader.AMBIENT_OCCLUSION);

		model.draw();
	}

	@Override
	protected int[] getAttachments()
	{
		return new int[]
		{
			ambientOcclusionBuffer.attachment
		};
	}

	public int ambientOcclusionInt()
	{
		return ambientOcclusionBuffer.id;
	}
}

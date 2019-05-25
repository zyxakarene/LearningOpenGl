package zyx.opengl.buffers;

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.FullScreenQuadModel;
import zyx.opengl.shaders.AbstractShader;
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

	private int noiseTextureId;

	private FullScreenQuadModel model;

	public static AmbientOcclusionRenderer getInstance()
	{
		return instance;
	}
	private TextureFromInt noiseTexture;

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
	void onBuffersCreated()
	{
		createNoiseTexture();
		
		int positionTextureId = DeferredRenderer.getInstance().positionInt();
		positionTexture = new TextureFromInt(w, h, positionTextureId, TextureSlot.SLOT_0);

		int normalTextureId = DeferredRenderer.getInstance().normalInt();
		normalTexture = new TextureFromInt(w, h, normalTextureId, TextureSlot.SLOT_1);

		noiseTexture = new TextureFromInt(4, 4, noiseTextureId, TextureSlot.SLOT_2);

		model = new FullScreenQuadModel(Shader.AMBIENT_OCCLUSION, positionTexture, normalTexture, noiseTexture);
	}

	public void drawAmbientOcclusion()
	{
		bindBuffer();
		ShaderManager.getInstance().bind(Shader.AMBIENT_OCCLUSION);		
		AbstractShader aoShader = ShaderManager.getInstance().get(Shader.AMBIENT_OCCLUSION);
		aoShader.upload();
		
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

	private void createNoiseTexture()
	{
		noiseTextureId = GL11.glGenTextures();

		Random random = new Random();
		float[] noiseData = new float[16 * 3];
		for (int i = 0; i < noiseData.length; i += 3)
		{
			float valueX = random.nextFloat() * 2f - 1;
			float valueY = random.nextFloat() * 2f - 1;
			float valueZ = 0;

			noiseData[i + 0] = valueX;
			noiseData[i + 1] = valueY;
			noiseData[i + 2] = valueZ;
		}

		FloatBuffer noiseTextureBuffer = BufferUtils.createFloatBuffer(16 * 3);
		noiseTextureBuffer.put(noiseData);
		noiseTextureBuffer.flip();
				
		glBindTexture(GL_TEXTURE_2D, noiseTextureId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL30.GL_RGB16F, 4, 4, 0, GL_RGB, GL_FLOAT, noiseTextureBuffer);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	}
}

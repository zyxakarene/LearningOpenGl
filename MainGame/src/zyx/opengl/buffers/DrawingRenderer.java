package zyx.opengl.buffers;

import java.util.ArrayList;
import zyx.engine.utils.ScreenSize;
import zyx.game.controls.input.MouseData;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.FullScreenQuadModel;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.*;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.opengl.textures.enums.TextureFormat;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.Bresenham2D;
import zyx.utils.FloatMath;
import zyx.utils.cheats.Print;
import zyx.utils.math.Vector2Int;

public class DrawingRenderer extends BaseFrameBuffer
{

	private static DrawingRenderer instance = new DrawingRenderer();

	private FrameBufferTexture underlayBuffer;

	private DrawingTexture underlayTexture;
	private DrawingTexture overlayTexture;

	private FullScreenQuadModel model;

	public static DrawingRenderer getInstance()
	{
		return instance;
	}
	private float b;
	private float g;
	private float r;

	public DrawingRenderer()
	{
		super(Buffer.DRAWING, 1f);
	}

	@Override
	protected void onCreateFrameBufferTextures()
	{
		underlayBuffer = new FrameBufferTexture(w, h, TextureAttachment.ATTACHMENT_0, TextureFormat.FORMAT_3_CHANNEL_16F);
	}

	@Override
	void onBuffersCreated()
	{
		int textureSize = 256;
		
		overlayTexture = new DrawingTexture(textureSize, textureSize, TextureSlot.DRAW_OVERLAY);
		underlayTexture = new DrawingTexture(textureSize, textureSize, TextureSlot.DRAW_UNDERLAY);

		model = new FullScreenQuadModel(Shader.DRAW, underlayTexture, overlayTexture);
	}

	private boolean isDrawing = false;

	public void draw()
	{
		boolean wasDrawing = isDrawing;
		isDrawing = MouseData.data.isLeftDown();

		if (!wasDrawing && isDrawing)
		{
			r = FloatMath.random();
			g = FloatMath.random();
			b = FloatMath.random();
		}
		
		if (isDrawing)
		{
			int x = MouseData.data.x;
			int y = MouseData.data.y;
			int dx = MouseData.data.dX;
			int dy = MouseData.data.dY;

			ArrayList<Vector2Int> line = Bresenham2D.getLineBetween(x + dx, y + dy, x, y);
			for (Vector2Int point : line)
			{
				overlayTexture.setPixelColor(point.x, point.y, 0.25f, 0, 0);
			}
		}

		if (wasDrawing && !isDrawing)
		{
			float[] over = overlayTexture.getPixelData();
			float[] under = underlayTexture.getPixelData();

			blendDarken(under, over, under);

			underlayTexture.setPixelColor(under);
			overlayTexture.setPixelColor(1, 1, 1);
		}

		bindBuffer();
		ShaderManager.getInstance().bind(Shader.DRAW);
		AbstractShader drawShader = ShaderManager.getInstance().get(Shader.DRAW);
		drawShader.upload();

		GLUtils.disableDepthWrite();
		model.draw();
		GLUtils.enableDepthWrite();
	}

	@Override
	protected int[] getAttachments()
	{
		return new int[]
		{
			underlayBuffer.attachment,
		};
	}

	public int underlayInt()
	{
		return underlayBuffer.id;
	}

	public int overlayInt()
	{
		return 0;
	}

	@Override
	protected void onDispose()
	{
		if (underlayBuffer != null)
		{
			underlayBuffer.dispose();
			underlayBuffer = null;
		}

		if (model != null)
		{
			model.dispose();
			model = null;
		}
	}

	private void blendDarken(float[] base, float[] blend, float[] out)
	{
		float opacity = 0.33f;
		int len = base.length;
		for (int i = 0; i < len; i++)
		{
			float baseValue = base[i];
			float blendValue = blend[i];
			
			out[i] = FloatMath.min(baseValue, blendValue) * opacity + baseValue * (1f - opacity);
		}
		
		/*
		float blendDarken(float base, float blend)
		{
			return min(blend,base);
		}

		vec3 blendDarken(vec3 base, vec3 blend)
		{
			float r = blendDarken(base.r, blend.r);
			float g = blendDarken(base.g, blend.g);
			float b = blendDarken(base.b, blend.b);
			return vec3(r, g ,b);
		}

		vec3 blendDarken(vec3 base, vec3 blend, float opacity)
		{
			return blendDarken(base, blend) * opacity + base * (1.0 - opacity);
		}
		 */
	}
}

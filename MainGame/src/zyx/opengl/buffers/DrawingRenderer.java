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
	private int textureSize;

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
		textureSize = 256;

		overlayTexture = new DrawingTexture(textureSize, textureSize, TextureSlot.DRAW_OVERLAY);
		underlayTexture = new DrawingTexture(textureSize, textureSize, TextureSlot.DRAW_UNDERLAY);

		overlayTexture.setBrushColor(1, 0, 0, 0.15f, 0.05f);

		model = new FullScreenQuadModel(Shader.DRAW, underlayTexture, overlayTexture);
	}

	public void draw()
	{
		bindBuffer();
		ShaderManager.getInstance().bind(Shader.DRAW);
		AbstractShader drawShader = ShaderManager.getInstance().get(Shader.DRAW);
		drawShader.upload();

		GLUtils.disableDepthWrite();
		GLUtils.disableDepthTest();
		model.draw();
		GLUtils.enableDepthTest();
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
		int len = base.length;
		for (int i = 0; i < len; i += 4)
		{
			float baseR = base[i + 0];
			float baseG = base[i + 1];
			float baseB = base[i + 2];
			float blendR = blend[i + 0];
			float blendG = blend[i + 1];
			float blendB = blend[i + 2];
			float blendA = blend[i + 3];

			float blendResultR = FloatMath.min(baseR, blendR) * blendA + baseR * (1f - blendA);
			float blendResultG = FloatMath.min(baseG, blendG) * blendA + baseG * (1f - blendA);
			float blendResultB = FloatMath.min(baseB, blendB) * blendA + baseB * (1f - blendA);
			out[i + 0] = blendResultR;
			out[i + 1] = blendResultG;
			out[i + 2] = blendResultB;
			out[i + 3] = 1;
		}
	}

	private int lastX = -999;
	private int lastY = -999;

	public void toggleDraw(boolean isDrawing)
	{
		if (isDrawing)
		{
			r = FloatMath.random();
			g = FloatMath.random();
			b = FloatMath.random();
			overlayTexture.setBrushColor(r, g, b, 0.33f, 0.15f);
		}
		else
		{
			float[] over = overlayTexture.getPixelData();
			float[] under = underlayTexture.getPixelData();

			blendDarken(under, over, under);

			underlayTexture.setPixelColor(under);
			overlayTexture.setPixelColor(1, 1, 1, 1);

			lastX = -999;
			lastY = -999;
		}
	}

	public void drawAt(float x, float y, boolean firstClick)
	{
		int xPos = (int) (x * textureSize);
		int yPos = (int) (y * textureSize);
		yPos = textureSize - yPos;
		
		if (firstClick)
		{
			lastX = xPos;
			lastY = yPos;
		}
		
		int dx = lastX - xPos;
		int dy = lastY - yPos;
		lastX = xPos;
		lastY = yPos;

		ArrayList<Vector2Int> line = Bresenham2D.getLineBetween(xPos + dx, yPos + dy, xPos, yPos);
		for (Vector2Int point : line)
		{
			overlayTexture.setPixelColor(point.x, point.y, 1);
			overlayTexture.setPixelColor(point.x + 1, point.y, 0.5f);
			overlayTexture.setPixelColor(point.x - 1, point.y, 0.5f);
			overlayTexture.setPixelColor(point.x, point.y + 1, 0.5f);
			overlayTexture.setPixelColor(point.x, point.y - 1, 0.5f);
			overlayTexture.setPixelColor(point.x + 1, point.y + 1, 0.33f);
			overlayTexture.setPixelColor(point.x + 1, point.y - 1, 0.33f);
			overlayTexture.setPixelColor(point.x - 1, point.y + 1, 0.33f);
			overlayTexture.setPixelColor(point.x - 1, point.y - 1, 0.33f);
		}
	}
}

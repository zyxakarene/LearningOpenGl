package zyx.opengl.textures;

import java.util.HashMap;
import zyx.engine.utils.ScreenSize;
import zyx.opengl.textures.custom.ITexture;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.opengl.textures.impl.CustomDataTexture;
import zyx.utils.cheats.Print;

public class DrawingTexture extends AbstractTexture
{

	private CustomDataTexture texture;
	private float[] pixelData;
	private boolean changed;
	
	private int w;
	private int h;
	
	private float brushR;
	private float brushG;
	private float brushB;
	private float brushStartA;
	private float brushUpdateA;

	public DrawingTexture(int width, int height, TextureSlot slot)
	{
		super("ColorTexture_0x", slot);

		w = width;
		h = height;
		
		int pixelCount = width * height;
		int floatData = pixelCount * 4;

		pixelData = new float[floatData];
		for (int i = 0; i < floatData; i++)
		{
			pixelData[i] = 1;
		}

		texture = new CustomDataTexture(width, height, pixelData);
		setSizes(width, height);
		
		setBrushColor(0, 0, 0, 1, 0);
	}

	public void setBrushColor(float r, float g, float b, float startA, float updateA)
	{
		brushR = r;
		brushG = g;
		brushB = b;
		brushStartA = startA;
		brushUpdateA = updateA;
	}

	public void setPixelColor(float[] data)
	{
		if (data.length == pixelData.length)
		{
			map.clear();
			System.arraycopy(data, 0, pixelData, 0, data.length);
			changed = true;
		}
	}
	
	public void setPixelColor(float r, float g, float b, float a)
	{
		map.clear();
		int pixelCount = w * h;
		for (int i = 0; i < pixelCount; i++)
		{
			int index = i * 4;
			pixelData[index + 0] = r;
			pixelData[index + 1] = g;
			pixelData[index + 2] = b;
			pixelData[index + 3] = a;
		}
		changed = true;
	}
	
	private HashMap<Integer, Boolean> map = new HashMap<>();
	
	public void setPixelColor(int x, int y, float multiplier)
	{
//		x = (int) (((float) x / (float) ScreenSize.width) * w);
//		y = (int) (((float) y / (float) ScreenSize.height) * h);

		if (x <= 0 || x >= w-2 || y <= 0 || y >= h-1)
		{
			//Out of bounds, ignore it
			return;
		}
		
		int index = (x + (y * h)) * 4;
		if (map.containsKey(index))
		{
			pixelData[index + 3] += (brushUpdateA * multiplier);
		}
		else
		{
			map.put(index, true);
			pixelData[index + 0] = brushR;
			pixelData[index + 1] = brushG;
			pixelData[index + 2] = brushB;
			pixelData[index + 3] = (brushStartA * multiplier);
		}
		changed = true;
	}

	@Override
	protected void onBind()
	{
		texture.bind();

		if (changed)
		{
			changed = false;
			texture.updateTextureFromData();
		}
	}

	@Override
	protected void onDispose()
	{
		if (texture != null)
		{
			texture.dispose();
			texture = null;
		}
	}

	public float[] getPixelData()
	{
		return pixelData;
	}

	@Override
	public ITexture getGlTexture()
	{
		return texture;
	}
}

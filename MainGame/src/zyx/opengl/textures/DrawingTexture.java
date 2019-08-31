package zyx.opengl.textures;

import java.util.HashMap;
import zyx.engine.utils.ScreenSize;
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

	public DrawingTexture(int width, int height, TextureSlot slot)
	{
		super("ColorTexture_0x", slot);

		w = width;
		h = height;
		
		int pixelCount = width * height;
		int floatData = pixelCount * 3;

		pixelData = new float[floatData];
		for (int i = 0; i < floatData; i++)
		{
			pixelData[i] = 1;
		}

		texture = new CustomDataTexture(width, height, pixelData);
		setSizes(width, height);
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
	
	public void setPixelColor(float r, float g, float b)
	{
		map.clear();
		int pixelCount = w * h;
		for (int i = 0; i < pixelCount; i++)
		{
			int index = i * 3;
			pixelData[index + 0] = r;
			pixelData[index + 1] = g;
			pixelData[index + 2] = b;
		}
		changed = true;
	}
	
	private HashMap<Integer, Boolean> map = new HashMap<>();
	
	public void setPixelColor(int x, int y, float r, float g, float b)
	{
		x = (int) (((float) x / (float) ScreenSize.width) * w);
		y = (int) (((float) y / (float) ScreenSize.height) * h);

		if (x < 0 || x >= w || y < 0 || y >= h)
		{
			//Out of bounds, ignore it
			return;
		}
		
		int index = (x + (y * h)) * 3;
		if (map.containsKey(index))
		{
			pixelData[index + 0] += r;
			pixelData[index + 1] += g;
			pixelData[index + 2] += b;
		}
		else
		{
			map.put(index, true);
			pixelData[index + 0] = r;
			pixelData[index + 1] = g;
			pixelData[index + 2] = b;
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
}

package zyx.opengl.textures.bitmapfont;

import zyx.utils.ListUtils;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import zyx.opengl.materials.impl.BitmapTextMaterial;
import zyx.opengl.textures.bitmapfont.alignment.HAlignment;
import zyx.opengl.textures.bitmapfont.alignment.VAlignment;

class TextGenerator
{

	private final LinkedList<Float> vertexData;
	private final LinkedList<Integer> elementData;
	private final FontFile fontFile;

	private HAlignment hAlign;
	private VAlignment vAlign;
	
	private char prevCharacter;

	private int highestX;
	private int highestY;

	private int currentValueX;
	private int currentValueY;
	private int currentElementCount;
	private float red;
	private float green;
	private float blue;
	private float alpha;
	private float scale;

	private float maxWidth;
	private float maxHeight;
	
	TextGenerator(BitmapTextMaterial material, float width, float height, float scale)
	{
		this.vertexData = new LinkedList<>();
		this.elementData = new LinkedList<>();
		this.fontFile = material.font;
		this.red = material.color.x;
		this.green = material.color.y;
		this.blue = material.color.z;
		this.alpha = material.alpha;
		this.scale = scale;

		this.maxWidth = width;
		this.maxHeight = height;

		hAlign = HAlignment.CENTER;
		vAlign = VAlignment.MIDDLE;
		
		currentValueX = 0;
		currentValueY = 0;

		highestX = 0;
		highestY = 0;
	}

	void sethAlign(HAlignment h, VAlignment v)
	{
		hAlign = h;
		vAlign = v;
	}

	void addCharacter(char character)
	{
		FontCharacter fontChar = fontFile.characterMap.get(character);
		FontKerning fontKerning = fontFile.getKerning(prevCharacter, character);

		if (character == KeyEvent.VK_SPACE)
		{
			//Don't add the space character. Just add some empty room
			currentValueX += (fontChar.xAdvance + fontKerning.amount) * scale;

			if (currentValueX >= maxWidth)
			{
				currentValueX = 0;
				currentValueY += (fontFile.lineHeight);
			}
			return;
		}
		else if (character == '\r' || character == '\n')
		{
			currentValueX = 0;
			currentValueY += (fontFile.lineHeight * scale);
			return;
		}

		float left = currentValueX + ((fontChar.xOffset + fontKerning.amount) * scale);
		float right = left + (fontChar.width * scale);
		float up = (-fontChar.yOffset - currentValueY) * scale;
		float down = up - (fontChar.height * scale);
				
		/**
		 * X, Y U, V R, G, B, A
		 */
		//Top left
		vertexData.add(left);
		vertexData.add(up);
		vertexData.add(fontChar.x);
		vertexData.add(fontChar.y);
		vertexData.add(red);
		vertexData.add(green);
		vertexData.add(blue);
		vertexData.add(alpha);

		//Top right
		vertexData.add(right);
		vertexData.add(up);
		vertexData.add(fontChar.x + fontChar.u);
		vertexData.add(fontChar.y);
		vertexData.add(red);
		vertexData.add(green);
		vertexData.add(blue);
		vertexData.add(alpha);

		//Bottom right
		vertexData.add(right);
		vertexData.add(down);
		vertexData.add(fontChar.x + fontChar.u);
		vertexData.add(fontChar.y + fontChar.v);
		vertexData.add(red);
		vertexData.add(green);
		vertexData.add(blue);
		vertexData.add(alpha);

		//Bottom left
		vertexData.add(left);
		vertexData.add(down);
		vertexData.add(fontChar.x);
		vertexData.add(fontChar.y + fontChar.v);
		vertexData.add(red);
		vertexData.add(green);
		vertexData.add(blue);
		vertexData.add(alpha);

		elementData.add(currentElementCount + 2);
		elementData.add(currentElementCount + 1);
		elementData.add(currentElementCount + 0);
		elementData.add(currentElementCount + 0);
		elementData.add(currentElementCount + 3);
		elementData.add(currentElementCount + 2);

		currentValueX += (fontChar.xAdvance + fontKerning.amount) * scale;
		currentElementCount += 4;
		prevCharacter = character;

		if (right > highestX)
		{
			highestX = (int) right;
		}
		if (down < -highestY)
		{
			highestY = (int) -down;
		}
	}

	private void scaleVertexData(float scale, float[] data)
	{
		float offsetX = 0;
		float offsetY = 0;
		
		switch (hAlign)
		{
			case LEFT:
				offsetX = 0;
				break;
			case CENTER:
				offsetX = ((maxWidth / 2) - (getWidth() * scale / 2));
				break;
			case RIGHT:
				offsetX = maxWidth - (getWidth() * scale);
				break;
		}
		
		switch (vAlign)
		{
			case TOP:
				offsetY = 0;
				break;
			case MIDDLE:
				offsetY = ((maxHeight / 2) - (getHeight() * scale / 2));
				break;
			case BOTTOM:
				offsetY = maxHeight - (getHeight() * scale);
				break;
		}

		highestX = 0;
		highestY = 0;

		int len = data.length;
		for (int i = 0; i < len; i += 8)
		{
			float xValue = data[i + 0] * scale;
			float yValue = data[i + 1] * scale;
			
			xValue += offsetX;
			yValue -= offsetY;
			
			if (xValue > highestX)
			{
				highestX = (int) xValue;
			}
			if (yValue < highestY)
			{
				highestY = (int) -yValue;
			}

			data[i + 0] = xValue;
			data[i + 1] = yValue;
		}
	}

	float[] getVertexData()
	{
		float[] finalizedVertexData = ListUtils.toFloatArray(vertexData);
		float scaleAmount = getRescaleValue();
		
		scaleVertexData(scaleAmount, finalizedVertexData);
		
		return finalizedVertexData;
	}

	int[] getElementData()
	{
		return ListUtils.toIntArray(elementData);
	}
	
	int getVertexCount()
	{
		return currentElementCount;
	}

	float getWidth()
	{
		return highestX;
	}

	float getHeight()
	{
		return highestY;
	}

	private float getRescaleValue()
	{
		float myWidth = getWidth();
		float myHeight = getHeight();

		if (myWidth > maxWidth || myHeight > maxHeight)
		{
			float scaleX = maxWidth / myWidth;
			float scaleY = maxHeight / myHeight;

			if (scaleX < scaleY)
			{
				return scaleX;
			}
			else
			{
				return scaleY;
			}
		}
		
		return 1;
	}
}

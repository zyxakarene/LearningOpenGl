package zyx.opengl.textures.bitmapfont;

import zyx.utils.ListUtils;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import org.lwjgl.util.vector.Vector4f;

class TextGenerator
{

	private final LinkedList<Float> vertexData;
	private final LinkedList<Integer> elementData;
	private final FontFile fontFile;

	private char prevCharacter;

	private int highestX;
	private int highestY;

	private int currentValueX;
	private int currentValueY;
	private int currentElementCount;
	private Vector4f color;
	private float scale;

	private float maxWidth;
	private float maxHeight;
	
	TextGenerator(FontFile fontFile, Vector4f color, float width, float height, float scale)
	{
		this.vertexData = new LinkedList<>();
		this.elementData = new LinkedList<>();
		this.fontFile = fontFile;
		this.color = color;
		this.scale = scale;

		this.maxWidth = width;
		this.maxHeight = height;

		currentValueX = 0;
		currentValueY = 0;

		highestX = 0;
		highestY = 0;
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
		vertexData.add(color.x);
		vertexData.add(color.y);
		vertexData.add(color.z);
		vertexData.add(color.w);

		//Top right
		vertexData.add(right);
		vertexData.add(up);
		vertexData.add(fontChar.x + fontChar.u);
		vertexData.add(fontChar.y);
		vertexData.add(color.x);
		vertexData.add(color.y);
		vertexData.add(color.z);
		vertexData.add(color.w);

		//Bottom right
		vertexData.add(right);
		vertexData.add(down);
		vertexData.add(fontChar.x + fontChar.u);
		vertexData.add(fontChar.y + fontChar.v);
		vertexData.add(color.x);
		vertexData.add(color.y);
		vertexData.add(color.z);
		vertexData.add(color.w);

		//Bottom left
		vertexData.add(left);
		vertexData.add(down);
		vertexData.add(fontChar.x);
		vertexData.add(fontChar.y + fontChar.v);
		vertexData.add(color.x);
		vertexData.add(color.y);
		vertexData.add(color.z);
		vertexData.add(color.w);

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
		float offsetX = ((maxWidth / 2) - (getWidth() * scale / 2));
		float offsetY = ((maxHeight / 2) - (getHeight() * scale / 2));
//		Print.out(scale);
//		Print.out(maxWidth, maxHeight);
//		Print.out(getWidth(), getHeight());
//		Print.out(offsetX, offsetY);
//		Print.out(highestY);
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
		if (scaleAmount < 1)
		{
		}
		
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

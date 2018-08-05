package zyx.opengl.textures.bitmapfont;

import java.util.LinkedList;
import org.lwjgl.util.vector.Vector4f;

class TextGenerator
{

	private final LinkedList<Float> vertexData;
	private final LinkedList<Integer> elementData;
	private final FontFile fontFile;

	private char prevCharacter;

	private int currentValueX;
	private int currentValueY;
	private int currentElementCount;
	private Vector4f color;

	TextGenerator(FontFile fontFile, Vector4f color)
	{
		this.vertexData = new LinkedList<>();
		this.elementData = new LinkedList<>();
		this.fontFile = fontFile;
		this.color = color;

		currentValueX = 0;
	}

	void addCharacter(char character)
	{
		FontCharacter fontChar = fontFile.characterMap.get(character);
		FontKerning fontKerning = fontFile.getKerning(prevCharacter, character);

		if (character == ' ')
		{
			//Don't add the space character. Just add some empty room
			currentValueX += fontChar.xAdvance + fontKerning.amount;
			return;
		}
		else if (character == '\n' || character == 13)
		{
			currentValueX = 0;
			currentValueY += fontFile.lineHeight;
			return;
		}

		float left = currentValueX + fontChar.xOffset + fontKerning.amount;
		float right = left + fontChar.width;
		float up = -fontChar.yOffset - currentValueY;
		float down = up - fontChar.height;

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

		currentValueX += fontChar.xAdvance + fontKerning.amount;
		currentElementCount += 4;
		prevCharacter = character;
	}

	/*
	
		//Position			Texcoords
		0, 0,				t.x, t.y, // Top-left
		100, 0,				t.u, t.y, // Top-right
		100, -100,			t.u, t.v, // Bottom-right
		0, -100,			t.x, t.v  // Bottom-left
	
	 */
 /*
	
		0, 1, 2,
		2, 3, 0
	
	 */
	float[] getVertexData()
	{
		return FontUtils.toFloatArray(vertexData);
	}

	int[] getElementData()
	{
		return FontUtils.toIntArray(elementData);
	}

	int getVertexCount()
	{
		return currentElementCount;
	}

	float getWidth()
	{
		return currentValueX;
	}

	float getHeight()
	{
		return currentValueY + fontFile.lineHeight;
	}
}

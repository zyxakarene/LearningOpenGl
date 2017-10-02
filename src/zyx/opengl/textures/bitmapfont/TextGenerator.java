package zyx.opengl.textures.bitmapfont;

import java.util.ArrayList;
import java.util.LinkedList;

class TextGenerator
{

	private final LinkedList<Float> vertexData;
	private final LinkedList<Integer> elementData;
	private final FontFile fontFile;
	
	private final ArrayList<Float> tempVertex;
	private final ArrayList<Integer> tempElement;
	
	private char prevCharacter;
	
	private int currentValueX;
	private int currentElementCount;

	TextGenerator(FontFile fontFile)
	{
		this.vertexData = new LinkedList<>();
		this.elementData = new LinkedList<>();
		this.fontFile = fontFile;
		
		this.tempVertex = new ArrayList<>(16);
		this.tempElement = new ArrayList<>(6);
		
		currentValueX = 0;
	}

	void addCharacter(char character)
	{
		tempVertex.clear();
		tempElement.clear();
		
		FontCharacter fontChar = fontFile.characterMap.get(character);
		FontKerning FontKerning = fontFile.getKerning(prevCharacter, character);
		
		//Top left
		tempVertex.add(currentValueX + fontChar.xOffset);
		tempVertex.add(-fontChar.yOffset);
		tempVertex.add(fontChar.x);
		tempVertex.add(fontChar.y);
		
		//Top right
		tempVertex.add(currentValueX + fontChar.xOffset + fontChar.xAdvance);
		tempVertex.add(-fontChar.yOffset);
		tempVertex.add(fontChar.x + fontChar.u);
		tempVertex.add(fontChar.y);
		
		//Bottom right
		tempVertex.add(currentValueX + fontChar.xOffset + fontChar.xAdvance);
		tempVertex.add(-fontChar.yOffset - fontChar.height);
		tempVertex.add(fontChar.x + fontChar.u);
		tempVertex.add(fontChar.y + fontChar.v);
		
		//Bottom left
		tempVertex.add(currentValueX + fontChar.xOffset);
		tempVertex.add(-fontChar.yOffset - fontChar.height);
		tempVertex.add(fontChar.x);
		tempVertex.add(fontChar.y + fontChar.v);		
		
		tempElement.add(currentElementCount + 0);
		tempElement.add(currentElementCount + 1);
		tempElement.add(currentElementCount + 2);
		tempElement.add(currentElementCount + 2);
		tempElement.add(currentElementCount + 3);
		tempElement.add(currentElementCount + 0);
		
		vertexData.addAll(tempVertex);
		elementData.addAll(tempElement);
		
		System.out.println(prevCharacter + "/" + character + "=" + FontKerning.amount);
		
		currentValueX += fontChar.xAdvance;
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

}

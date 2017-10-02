package zyx.opengl.textures.bitmapfont;

import java.io.DataInputStream;
import java.io.IOException;
import zyx.opengl.textures.GameTexture;

class FontCharacter
{
	public char id;
	public float x;
	public float y;
	public float u;
	public float v;
	public float width;
	public float height;
	public float xOffset;
	public float yOffset;
	public float xAdvance;

	FontCharacter(GameTexture mainTexture, DataInputStream in) throws IOException
	{
		float w = mainTexture.width;
		float h = mainTexture.height;
		
		id = in.readChar();
		x = in.readShort();
		y = in.readShort();
		x = x / w;
		y = y / h;
		width = in.readShort();
		height = in.readShort();
		xOffset = in.readShort();
		yOffset = in.readShort();
		xAdvance = in.readShort();
		
		u = width / w; 
		v = height / h; 
	}
}

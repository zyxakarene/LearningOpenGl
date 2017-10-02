package zyx.opengl.textures.bitmapfont;

import java.io.DataInputStream;
import java.io.IOException;

class FontKerning
{

	public char first;
	public char second;
	public byte amount;

	FontKerning()
	{
		amount = 0;
	}
	
	FontKerning(DataInputStream in) throws IOException
	{
		first = in.readChar();
		second = in.readChar();
		amount = in.readByte();
	}
}

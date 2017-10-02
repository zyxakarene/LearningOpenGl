package zyx.opengl.textures.bitmapfont;

import java.io.*;
import zyx.opengl.textures.GameTexture;

public class BitmapFontGenerator
{

	private final FontFile fontFile;
	private final GameTexture mainTexture;

	public BitmapFontGenerator(GameTexture mainTexture)
	{
		this.fontFile = new FontFile();
		this.mainTexture = mainTexture;
	}

	public void loadFromFnt(File file) throws IOException
	{
		byte[] buffer;
		try (RandomAccessFile raf = new RandomAccessFile(file, "r"))
		{
			buffer = new byte[(int) raf.length()];
			raf.read(buffer, 0, buffer.length);
		}

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(buffer));
		
		short characters = in.readShort();
		short kernings = in.readShort();
		
		FontCharacter character;
		FontKerning kerning;
		for (int i = 0; i < characters; i++)
		{
			character = new FontCharacter(mainTexture, in);
			fontFile.characters.add(character);
		}
		
		for (int i = 0; i < kernings; i++)
		{
			kerning = new FontKerning(in);
			fontFile.kernings.add(kerning);
		}
		
		fontFile.generateMaps();
	}

	public BitmapFont createBitmapFont()
	{
		return new BitmapFont(mainTexture, fontFile);
	}
}

package zyx.opengl.textures.bitmapfont;

import java.io.*;
import zyx.opengl.textures.AbstractTexture;

public class BitmapFontGenerator
{
	private BitmapFont font;
	private FontFile fontFile;
	private DataInputStream fontData;

	public BitmapFontGenerator(DataInputStream fontData)
	{
		this.fontFile = new FontFile();
		this.fontData = fontData;
	}

	private void buildBitmapFont(float w, float h) throws IOException
	{
		short lineHeight = fontData.readShort();
		short characters = fontData.readShort();
		short kernings = fontData.readShort();
		
		FontCharacter character;
		FontKerning kerning;
		for (int i = 0; i < characters; i++)
		{
			character = new FontCharacter(w, h, fontData);
			fontFile.characters.add(character);
		}
		
		for (int i = 0; i < kernings; i++)
		{
			kerning = new FontKerning(fontData);
			fontFile.kernings.add(kerning);
		}
		
		fontFile.lineHeight = lineHeight;
		
		fontFile.generateMaps();
	}

	public BitmapFont createBitmapFont(AbstractTexture texture)
	{
		if (font == null)
		{
			try
			{
				float w = texture.getWidth();
				float h = texture.getHeight();
				buildBitmapFont(w, h);
			}
			catch (IOException ex)
			{
				throw new RuntimeException("Bitmap font could not be build", ex);
			}
			
			font = new BitmapFont(texture, fontFile);
		}
		
		return font;
	}

	public void dispose()
	{
		fontData = null;
		
		if(fontFile != null)
		{
			fontFile.dispose();
			fontFile = null;
		}
		
		if (font != null)
		{
			font.dispose();
			font = null;
		}
	}
}

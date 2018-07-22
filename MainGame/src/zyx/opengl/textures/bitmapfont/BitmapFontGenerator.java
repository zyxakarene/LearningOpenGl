package zyx.opengl.textures.bitmapfont;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import zyx.opengl.textures.AbstractTexture;

public class BitmapFontGenerator
{

	private FontFile fontFile;
	
	private AbstractTexture mainTexture;
	private DataInputStream fontData;

	public BitmapFontGenerator()
	{
		this.fontFile = new FontFile();
	}

	public void setMainTexture(AbstractTexture mainTexture)
	{
		this.mainTexture = mainTexture;
	}

	public void setFontData(DataInputStream fontData)
	{
		this.fontData = fontData;
	}

	private void buildBitmapFont() throws IOException
	{
		String resource = fontData.readUTF();
		short lineHeight = fontData.readShort();
		short characters = fontData.readShort();
		short kernings = fontData.readShort();
		
		FontCharacter character;
		FontKerning kerning;
		for (int i = 0; i < characters; i++)
		{
			character = new FontCharacter(mainTexture, fontData);
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

	public BitmapFont createBitmapFont()
	{
		try
		{
			buildBitmapFont();
		}
		catch (IOException ex)
		{
			Logger.getLogger(BitmapFontGenerator.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return new BitmapFont(mainTexture, fontFile);
	}

	public void dispose()
	{
		mainTexture = null;
		fontData = null;
		
		if(fontFile != null)
		{
			fontFile.dispose();
			fontFile = null;
		}
	}
}

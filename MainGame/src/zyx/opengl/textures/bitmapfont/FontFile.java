package zyx.opengl.textures.bitmapfont;

import java.util.ArrayList;
import java.util.HashMap;

class FontFile
{
	private static final FontKerning NULL_KERNING = new FontKerning();
	
	float lineHeight;
	
	ArrayList<FontCharacter> characters = new ArrayList<>();
	ArrayList<FontKerning> kernings = new ArrayList<>();

	HashMap<Character, FontCharacter> characterMap = new HashMap<>();
	HashMap<Character, FontKerning> kerningMap = new HashMap<>();
	
	void generateMaps()
	{
		for (FontCharacter character : characters)
		{
			characterMap.put(character.id, character);
		}
		
		for (FontKerning kerning : kernings)
		{
			char key = (char) ((kerning.second << 7) + kerning.first);
			kerningMap.put(key, kerning);
		}
	}
	
	FontKerning getKerning(char prev, char current)
	{
		char key = (char) ((current << 7) + prev);

		if (kerningMap.containsKey(key))
		{
			return kerningMap.get(key);
		}
		
		return NULL_KERNING;
	}

	void dispose()
	{
		characters.clear();
		kernings.clear();
		characterMap.clear();
		kerningMap.clear();
		
		characters = null;
		kernings = null;
		characterMap = null;
		kerningMap = null;
	}
}

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
		int len = characters.size();
		FontCharacter character;
		for (int i = 0; i < len; i++)
		{
			character = characters.get(i);
			characterMap.put(character.id, character);
		}
		
		char key;
		len = kernings.size();
		FontKerning kerning;
		for (int i = 0; i < len; i++)
		{
			kerning = kernings.get(i);
			
			key = (char) ((kerning.second << 7) + kerning.first);
			
			kerningMap.put(key, kerning);
		}
		
//		int key = (b << 7) + a;
//		
//		char readB = (char) (key >> 7);
//		char readA = (char) (key & 0xFF);
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
}

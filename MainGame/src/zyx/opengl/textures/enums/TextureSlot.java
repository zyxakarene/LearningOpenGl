package zyx.opengl.textures.enums;

import org.lwjgl.opengl.GL13;

public enum TextureSlot
{
	SHARED_DIFFUSE(0, GL13.GL_TEXTURE0),
	
	WORLD_NORMAL(1, GL13.GL_TEXTURE1),
	WORLD_SPECULAR(2, GL13.GL_TEXTURE2),
	WORLD_CUBEMAPS(10, GL13.GL_TEXTURE10),	
	
	DEFERRED_POSITION(0, GL13.GL_TEXTURE0),
	DEFERRED_NORMAL(1, GL13.GL_TEXTURE1),
	DEFERRED_COLOR_SPEC(2, GL13.GL_TEXTURE2),
	DEFERRED_DEPTH(3, GL13.GL_TEXTURE3),
	DEFERRED_SHADOW(4, GL13.GL_TEXTURE4),
	DEFERRED_AO(5, GL13.GL_TEXTURE5),
	
	AO_SCREEN_POSITION(0, GL13.GL_TEXTURE0),
	AO_SCREEN_NORMAL(1, GL13.GL_TEXTURE1),
	AO_NOISE(2, GL13.GL_TEXTURE2);

	public static final int COUNT = TextureSlot.values().length;
	
	public final int index;
	public final int glSlot;
	
	private TextureSlot(int index, int glSlot)
	{
		this.index = index;
		this.glSlot = glSlot;
	}
}

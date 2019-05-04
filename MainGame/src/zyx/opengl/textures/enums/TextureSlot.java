package zyx.opengl.textures.enums;

import org.lwjgl.opengl.GL13;

public enum TextureSlot
{
	SLOT_0(0, GL13.GL_TEXTURE0),
	SLOT_1(1, GL13.GL_TEXTURE1),
	SLOT_2(2, GL13.GL_TEXTURE2),
	SLOT_3(3, GL13.GL_TEXTURE3),
	SLOT_4(4, GL13.GL_TEXTURE4),
	SLOT_5(5, GL13.GL_TEXTURE5);

	public static final int COUNT = 6;
	
	public final int index;
	public final int glSlot;
	
	private TextureSlot(int index, int glSlot)
	{
		this.index = index;
		this.glSlot = glSlot;
	}
}

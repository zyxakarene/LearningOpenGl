package zyx.opengl.textures.enums;

import org.lwjgl.opengl.GL30;

public enum TextureAttachment
{
	ATTACHMENT_0(0, GL30.GL_COLOR_ATTACHMENT0),
	ATTACHMENT_1(1, GL30.GL_COLOR_ATTACHMENT1),
	ATTACHMENT_2(2, GL30.GL_COLOR_ATTACHMENT2),
	ATTACHMENT_3(3, GL30.GL_COLOR_ATTACHMENT3),
	ATTACHMENT_4(4, GL30.GL_COLOR_ATTACHMENT4),
	ATTACHMENT_5(5, GL30.GL_COLOR_ATTACHMENT5),
	ATTACHMENT_6(6, GL30.GL_COLOR_ATTACHMENT6);
	
	public final int index;
	public final int glAttachment;
	
	private TextureAttachment(int index, int glAttachment)
	{
		this.index = index;
		this.glAttachment = glAttachment;
	}
}

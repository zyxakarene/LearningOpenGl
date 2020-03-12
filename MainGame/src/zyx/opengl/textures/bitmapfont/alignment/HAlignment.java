package zyx.opengl.textures.bitmapfont.alignment;

public enum HAlignment
{
	LEFT("left"),
	CENTER("center"),
	RIGHT("right");
	
	private static final HAlignment[] ALL = values();
	
	public final String alignName;

	private HAlignment(String name)
	{
		this.alignName = name;
	}
	
	public static HAlignment getFrom(String name)
	{
		for (HAlignment alignment : ALL)
		{
			if (alignment.alignName.equals(name))
			{
				return alignment;
			}
		}
		
		return CENTER;
	}
}

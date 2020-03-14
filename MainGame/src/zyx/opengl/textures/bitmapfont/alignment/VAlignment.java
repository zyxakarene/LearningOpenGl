package zyx.opengl.textures.bitmapfont.alignment;

public enum VAlignment
{
	TOP("top"),
	MIDDLE("middle"),
	BOTTOM("bottom");
	
	private static final VAlignment[] ALL = values();
	
	public final String alignName;

	private VAlignment(String name)
	{
		this.alignName = name;
	}
	
	public static VAlignment getFrom(String name)
	{
		for (VAlignment alignment : ALL)
		{
			if (alignment.alignName.equals(name))
			{
				return alignment;
			}
		}
		
		return MIDDLE;
	}
}

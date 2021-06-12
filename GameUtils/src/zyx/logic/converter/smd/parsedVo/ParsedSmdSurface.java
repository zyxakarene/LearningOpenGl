package zyx.logic.converter.smd.parsedVo;

import java.util.ArrayList;

public class ParsedSmdSurface
{
	public final String name;
	public final ArrayList<ParsedSmdTriangle> triangles;

	public ParsedSmdSurface(String name)
	{
		this.name = name;
		triangles = new ArrayList<>();
	}
	
	
}

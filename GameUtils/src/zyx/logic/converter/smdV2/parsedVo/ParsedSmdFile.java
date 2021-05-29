package zyx.logic.converter.smdV2.parsedVo;

import java.util.ArrayList;
import java.util.HashMap;

public class ParsedSmdFile
{
	public final ArrayList<ParsedSmdBone> bones;
	public final ArrayList<ParsedSmdFrame> frames;
	public final HashMap<String, ParsedSmdSurface> surfaces;

	public ParsedSmdFile()
	{
		bones = new ArrayList<>();
		frames = new ArrayList<>();
		surfaces = new HashMap<>();
	}
	
}

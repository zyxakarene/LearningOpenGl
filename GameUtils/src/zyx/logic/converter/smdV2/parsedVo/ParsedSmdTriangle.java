package zyx.logic.converter.smdV2.parsedVo;

public class ParsedSmdTriangle
{
	public final ParsedSmdVertex v1;
	public final ParsedSmdVertex v2;
	public final ParsedSmdVertex v3;

	public ParsedSmdTriangle()
	{
		v1 = new ParsedSmdVertex();
		v2 = new ParsedSmdVertex();
		v3 = new ParsedSmdVertex();
	}
}

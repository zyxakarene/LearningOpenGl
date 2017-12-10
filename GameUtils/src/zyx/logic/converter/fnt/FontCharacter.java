package zyx.logic.converter.fnt;

import java.io.DataOutputStream;
import java.io.IOException;
import org.w3c.dom.NamedNodeMap;

class FontCharacter
{
	public char id;
	public short x;
	public short y;
	public short width;
	public short height;
	public short xOffset;
	public short yOffset;
	public short xAdvance;

	FontCharacter(NamedNodeMap attributes)
	{
		id = (char) Short.parseShort(attributes.getNamedItem("id").getNodeValue());
		x = Short.parseShort(attributes.getNamedItem("x").getNodeValue());
		y = Short.parseShort(attributes.getNamedItem("y").getNodeValue());
		width = Short.parseShort(attributes.getNamedItem("width").getNodeValue());
		height = Short.parseShort(attributes.getNamedItem("height").getNodeValue());
		xOffset = Short.parseShort(attributes.getNamedItem("xoffset").getNodeValue());
		yOffset = Short.parseShort(attributes.getNamedItem("yoffset").getNodeValue());
		xAdvance = Short.parseShort(attributes.getNamedItem("xadvance").getNodeValue());
	}
	
	void save(DataOutputStream out) throws IOException
	{
		out.writeChar(id);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		out.writeShort(xOffset);
		out.writeShort(yOffset);
		out.writeShort(xAdvance);
	}
}

package zyx.logic.converter.fnt;

import java.io.DataOutputStream;
import java.io.IOException;
import org.w3c.dom.NamedNodeMap;

public class FontKerning
{

	public char first;
	public char second;
	public byte amount;

	FontKerning(NamedNodeMap attributes)
	{
		first = (char) Short.parseShort(attributes.getNamedItem("first").getNodeValue());
		second = (char) Short.parseShort(attributes.getNamedItem("second").getNodeValue());
		amount = Byte.parseByte(attributes.getNamedItem("amount").getNodeValue());
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeChar(first);
		out.writeShort(second);
		out.writeByte(amount);
	}
}

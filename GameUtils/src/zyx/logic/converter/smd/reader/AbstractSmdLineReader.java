package zyx.logic.converter.smd.reader;

import java.util.Scanner;

public abstract class AbstractSmdLineReader
{
	protected static final String SPLIT_TEXT = " ";
	protected static final String TOKEN_END = "end";

	public AbstractSmdLineReader()
	{
	}
	
	abstract void readFrom(Scanner scan);
	
	protected short toShort(String[] split, int index)
	{
		return Short.parseShort(split[index]);
	}
	
	protected byte toByte(String[] split, int index)
	{
		return Byte.parseByte(split[index]);
	}
	
	protected float toFloat(String[] split, int index)
	{
		return Float.parseFloat(split[index]);
	}

	protected String toString(String[] split, int index)
	{
		String text = split[index];
		return text.replaceAll("\"", "");
	}
}

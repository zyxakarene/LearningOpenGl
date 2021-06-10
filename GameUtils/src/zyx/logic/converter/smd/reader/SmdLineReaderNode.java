package zyx.logic.converter.smd.reader;

import java.util.ArrayList;
import java.util.Scanner;
import zyx.logic.converter.smd.parsedVo.ParsedSmdBone;

class SmdLineReaderNode extends AbstractSmdLineReader
{

	private final static int ID_INDEX = 0;
	private final static int NAME_INDEX = 1;
	private final static int PARENT_INDEX = 2;

	private final ArrayList<ParsedSmdBone> bones;

	SmdLineReaderNode(ArrayList<ParsedSmdBone> bones)
	{
		this.bones = bones;
	}

	@Override
	void readFrom(Scanner scan)
	{
		while (scan.hasNextLine())
		{
			String line = scan.nextLine();
			if (line.equals(TOKEN_END))
			{
				return;
			}

			String[] split = line.split(SPLIT_TEXT);
			ParsedSmdBone bone = new ParsedSmdBone();
			bone.id = toByte(split, ID_INDEX);
			bone.name = toString(split, NAME_INDEX);
			bone.parentId = toByte(split, PARENT_INDEX);

			bones.add(bone);
		}
	}
}

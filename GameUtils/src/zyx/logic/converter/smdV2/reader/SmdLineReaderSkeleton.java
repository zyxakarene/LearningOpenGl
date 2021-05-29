package zyx.logic.converter.smdV2.reader;

import java.util.ArrayList;
import java.util.Scanner;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFrame;
import static zyx.logic.converter.smdV2.reader.AbstractSmdLineReader.TOKEN_END;

class SmdLineReaderSkeleton extends AbstractSmdLineReader
{

	private static final String TOKEN_TIME = "time";

	private final static int BONE_ID_INDEX = 0;
	private final static int POS_X_INDEX = 1;
	private final static int POS_Y_INDEX = 2;
	private final static int POS_Z_INDEX = 3;
	private final static int ROT_X_INDEX = 4;
	private final static int ROT_Y_INDEX = 5;
	private final static int ROT_Z_INDEX = 6;

	private final ArrayList<ParsedSmdFrame> frames;

	private ParsedSmdFrame activeFrame;

	SmdLineReaderSkeleton(ArrayList<ParsedSmdFrame> frames)
	{
		this.frames = frames;
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
			else if (line.startsWith(TOKEN_TIME))
			{
				short frameId = (short) frames.size();
				activeFrame = new ParsedSmdFrame(frameId);
				frames.add(activeFrame);
			}
			else
			{
				String[] split = line.split(SPLIT_TEXT);
				
				byte boneId = toByte(split, BONE_ID_INDEX);

				Vector3f pos = new Vector3f();
				pos.x = toFloat(split, POS_X_INDEX);
				pos.x = toFloat(split, POS_Y_INDEX);
				pos.x = toFloat(split, POS_Z_INDEX);
				Vector3f rot = new Vector3f();
				rot.x = toFloat(split, ROT_X_INDEX);
				rot.x = toFloat(split, ROT_Y_INDEX);
				rot.x = toFloat(split, ROT_Z_INDEX);

				activeFrame.positions.add(pos);
				activeFrame.rotations.add(rot);
				activeFrame.boneIds.add(boneId);
			}
		}
	}
}

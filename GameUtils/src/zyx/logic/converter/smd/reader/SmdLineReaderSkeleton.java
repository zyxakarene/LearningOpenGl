package zyx.logic.converter.smd.reader;

import java.util.ArrayList;
import java.util.Scanner;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.smd.parsedVo.ParsedSmdFrame;
import static zyx.logic.converter.smd.reader.AbstractSmdLineReader.TOKEN_END;

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
				pos.y = toFloat(split, POS_Y_INDEX);
				pos.z = toFloat(split, POS_Z_INDEX);
				Vector3f rot = new Vector3f();
				rot.x = toFloat(split, ROT_X_INDEX);
				rot.y = toFloat(split, ROT_Y_INDEX);
				rot.z = toFloat(split, ROT_Z_INDEX);

				Quaternion quat = eulerToQuat(rot);

				activeFrame.positions.add(pos);
				activeFrame.rotations.add(quat);
				activeFrame.boneIds.add(boneId);
			}
		}
	}

	/**
	 * Must be order ZYX
	 */
	private Quaternion eulerToQuat(Vector3f rot)
	{
		Quaternion q = new Quaternion();

		float c = (float) Math.cos(rot.x / 2f);
		float d = (float) Math.cos(rot.y / 2f);
		float e = (float) Math.cos(rot.z / 2f);
		float f = (float) Math.sin(rot.x / 2f);
		float g = (float) Math.sin(rot.y / 2f);
		float h = (float) Math.sin(rot.z / 2f);

		q.x = f * d * e - c * g * h;
		q.y = c * g * e + f * d * h;
		q.z = c * d * h - f * g * e;
		q.w = c * d * e + f * g * h;

		return q;
	}
}

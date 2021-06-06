package zyx.logic.converter.smdV2.parsedVo;

import java.util.ArrayList;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class ParsedSmdFrame
{
	public short frame;
	public final ArrayList<Byte> boneIds;
	public final ArrayList<Vector3f> positions;
	public final ArrayList<Quaternion> rotations;

	public ParsedSmdFrame(short frame)
	{
		this.frame = frame;
		boneIds = new ArrayList<>();
		positions = new ArrayList<>();
		rotations = new ArrayList<>();
	}
}

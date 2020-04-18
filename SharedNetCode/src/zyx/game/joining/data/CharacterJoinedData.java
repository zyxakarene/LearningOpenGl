package zyx.game.joining.data;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.CharacterType;
import zyx.game.vo.Gender;

public class CharacterJoinedData
{
	public static final CharacterJoinedData INSTANCE = new CharacterJoinedData();

	public static final int MAX_COUNT = 10;
	
	public int joinCount;
	
	public final Vector3f[] positions;
	public final Vector3f[] lookAts;
	public final int[] ids;
	public final Gender[] genders;
	public final CharacterType[] types;
	public final String[] names;

	private CharacterJoinedData()
	{
		joinCount = 0;
		
		positions = new Vector3f[MAX_COUNT];
		lookAts = new Vector3f[MAX_COUNT];
		ids = new int[MAX_COUNT];
		genders = new Gender[MAX_COUNT];
		types = new CharacterType[MAX_COUNT];
		names = new String[MAX_COUNT];
		
		for (int i = 0; i < MAX_COUNT; i++)
		{
			positions[i] = new Vector3f();
			lookAts[i] = new Vector3f();
			ids[i] = 0;
			genders[i] = Gender.MALE;
			types[i] = CharacterType.PLAYER;
			names[i] = "";
		}
	}
}

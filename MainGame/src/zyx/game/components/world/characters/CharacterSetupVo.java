package zyx.game.components.world.characters;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.joining.data.CharacterJoinedData;
import zyx.game.vo.CharacterType;
import zyx.game.vo.Gender;

public class CharacterSetupVo
{

	int id;
	Vector3f pos;
	Vector3f look;
	Gender gender;
	CharacterType type;
	String name;

	public void fromData(CharacterJoinedData data, int index)
	{
		id = data.ids[index];
		pos = data.positions[index];
		look = data.lookAts[index];
		gender = data.genders[index];
		type = data.types[index];
		name = data.names[index];
	}
}

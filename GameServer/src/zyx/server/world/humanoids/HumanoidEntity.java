package zyx.server.world.humanoids;

import zyx.game.vo.Gender;
import zyx.server.world.entity.WorldEntity;

public class HumanoidEntity extends WorldEntity
{
	public final String name;
	public final Gender gender;

	public HumanoidEntity(String name, Gender gender)
	{
		this.name = name;
		this.gender = gender;
	}

}

package zyx.game.vo;

public enum Gender
{
	MALE(1),
	FEMALE(2);
	
	private static final Gender[] values = values();

	public final int id;

	private Gender(int id)
	{
		this.id = id;
	}
	
	public static Gender getFromId(int id)
	{
		for (Gender gender : values)
		{
			if (gender.id == id)
			{
				return gender;
			}
		}
		
		String msg = String.format("No such Gender ID: %s", id);
		throw new IllegalArgumentException(msg);
	}
}

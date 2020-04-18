package zyx.game.vo;

public enum CharacterType
{
	CHEF(1),
	GUEST(2),
	CLEANER(3),
	PLAYER(4);
	
	private static final CharacterType[] values = values();

	public final int id;

	private CharacterType(int id)
	{
		this.id = id;
	}
	
	public static CharacterType getFromId(int id)
	{
		for (CharacterType type : values)
		{
			if (type.id == id)
			{
				return type;
			}
		}
		
		String msg = String.format("No such CharacterType ID: %s", id);
		throw new IllegalArgumentException(msg);
	}
}

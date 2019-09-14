package zyx.game.world.guests.data;

public class GuestPayData
{

	public static final GuestPayData INSTANCE = new GuestPayData();

	public int[] characterIds;
	public int[] payAmounts;

	private GuestPayData()
	{
	}
}

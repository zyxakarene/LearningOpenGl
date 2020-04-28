package zyx.game.world.guests.data;

public class GuestOrderData
{
	public static final GuestOrderData INSTANCE = new GuestOrderData();
	
	public int characterId;
	public int dishTypeId;

	private GuestOrderData()
	{
	}
}

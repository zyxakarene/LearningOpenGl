package zyx.game.world.guests.data;

public class GuestGrabData
{
	public static final GuestGrabData INSTANCE = new GuestGrabData();
	
	public int characterId;
	public int foodId;
	public boolean correct;

	private GuestGrabData()
	{
	}
}

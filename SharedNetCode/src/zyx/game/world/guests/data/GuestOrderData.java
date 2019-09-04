package zyx.game.world.guests.data;

import zyx.game.vo.DishType;

public class GuestOrderData
{
	public static final GuestOrderData INSTANCE = new GuestOrderData();
	
	public int characterId;
	public DishType dishType;

	private GuestOrderData()
	{
	}
}

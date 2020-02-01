package zyx.server.world.humanoids.handheld;

import java.util.ArrayList;

public class HandheldItemList
{
	private static final ArrayList<HandheldItem> CURRENT_ITEMS = new ArrayList<>();
	
	static void addItem(HandheldItem item)
	{
		CURRENT_ITEMS.add(item);
	}
	
	static void removeItem(HandheldItem item)
	{
		CURRENT_ITEMS.remove(item);
	}

	public static ArrayList<HandheldItem> getAllItems()
	{
		return CURRENT_ITEMS;
	}
}

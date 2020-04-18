package zyx.game.models.impl;

import zyx.engine.components.world.WorldObject;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.engine.utils.worldpicker.IWorldPickedItem;
import zyx.game.components.world.characters.GameCharacter;
import zyx.game.components.world.furniture.BaseFurnitureItem;
import zyx.game.components.world.items.GameItem;

public class SelectionModel implements IWorldPickedItem
{
	public GameItem lastInteractedItem;
	public BaseFurnitureItem lastInteractedFurniture;
	public GameCharacter lastInteractedCharacter;
	
	@Override
	public void onGeometryPicked(ClickedInfo info)
	{
		lastInteractedItem = null;
		lastInteractedFurniture = null;
		lastInteractedCharacter = null;
		
		WorldObject worldObject = info.worldObject;
		if (worldObject instanceof GameItem)
		{
			lastInteractedItem = (GameItem) worldObject;
		}
		else if (worldObject instanceof BaseFurnitureItem)
		{
			lastInteractedFurniture = (BaseFurnitureItem) worldObject;
		}
		else if (worldObject instanceof GameCharacter)
		{
			lastInteractedCharacter = (GameCharacter) worldObject;
		}
	}
}

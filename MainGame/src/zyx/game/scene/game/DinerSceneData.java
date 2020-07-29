package zyx.game.scene.game;

import java.util.HashMap;
import zyx.game.components.world.characters.GameCharacter;
import zyx.game.components.world.furniture.BaseFurnitureItem;
import zyx.game.scene.ItemHandler;
import zyx.game.scene.ItemHolderHandler;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class DinerSceneData implements IDisposeable, IUpdateable
{
	public final ItemHandler itemHandler;
	public final ItemHolderHandler holderHandler;
	
	public final HashMap<Integer, GameCharacter> characterMap;
	public final HashMap<Integer, BaseFurnitureItem> furnitureMap;

	public DinerSceneData(DinerScene scene)
	{
		holderHandler = new ItemHolderHandler(scene);
		itemHandler = new ItemHandler(holderHandler);
		
		characterMap = new HashMap<>();
		furnitureMap = new HashMap<>();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		holderHandler.update(timestamp, elapsedTime);
	}

	@Override
	public void dispose()
	{
		holderHandler.dispose();
		itemHandler.dispose();
		
		characterMap.clear();
		furnitureMap.clear();
	}
	

}

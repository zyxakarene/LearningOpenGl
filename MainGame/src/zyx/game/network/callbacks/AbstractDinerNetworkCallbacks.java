package zyx.game.network.callbacks;

import java.util.HashMap;
import zyx.engine.scene.SceneManager;
import zyx.game.components.world.characters.GameCharacter;
import zyx.game.components.world.furniture.BaseFurnitureItem;
import zyx.game.scene.ItemHandler;
import zyx.game.scene.ItemHolderHandler;
import zyx.game.scene.game.DinerScene;
import zyx.game.scene.game.DinerSceneData;
import zyx.net.io.controllers.NetworkCallbacks;

public abstract class AbstractDinerNetworkCallbacks extends NetworkCallbacks
{
	protected ItemHolderHandler itemHolderHandler;
	protected ItemHandler itemHandler;

	protected HashMap<Integer, GameCharacter> characterMap;
	protected HashMap<Integer, BaseFurnitureItem> furnitureMap;

	public AbstractDinerNetworkCallbacks()
	{
		DinerScene dinerScene = SceneManager.getInstance().<DinerScene>getSceneAs();
		
		DinerSceneData sceneData = dinerScene.sceneData;
		itemHolderHandler = sceneData.holderHandler;
		itemHandler = sceneData.itemHandler;
		characterMap = sceneData.characterMap;
		furnitureMap = sceneData.furnitureMap;
	}
}

package zyx.game.components.world.furniture;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.World3D;
import zyx.engine.scene.SceneManager;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.GameItem;
import zyx.game.components.world.player.PlayerObject;
import zyx.game.models.GameModels;
import zyx.game.scene.game.DinerScene;

public class Floor extends BaseFurnitureItem<SimpleMesh>
{

	private PlayerObject player;
	private World3D world;

	public Floor()
	{
		super(false);
		
		player = SceneManager.getInstance().<DinerScene>getSceneAs().player;
		world = World3D.getInstance();
	}

	@Override
	protected String getResource()
	{
		return "mesh.furniture.floor";
	}

	@Override
	protected void onGotItem(GameItem item)
	{
		world.addChild(item);
		item.setRotation(0, 0, 0);
		item.setPosition(false, 0, 0, 0);
	}

	@Override
	protected void onLostItem(GameItem item)
	{
		world.removeChild(item);
	}
	
	@Override
	public boolean isInteractable()
	{
		return true;
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (player != null)
		{
			Vector3f pos = player.getPosition(false, null);
			pos.z = 0;
			
			setPosition(false, pos);
		}
	}
	
	@Override
	public ArrayList<InteractionAction> getInteractions()
	{
		ArrayList<InteractionAction> options = new ArrayList<>();
		
		if (GameModels.player.carriedItem != null)
		{
			options.add(InteractionAction.PLACE);
		}
		
		return options;
	}
}

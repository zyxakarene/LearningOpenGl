package zyx.game.components.world.player;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import zyx.game.components.GameObject;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.GameItem;
import zyx.game.models.GameModels;
import zyx.opengl.models.implementations.physics.PhysBox;

public class PlayerObject extends GameObject implements IItemHolder
{

	private PlayerClipboard board;

	public PlayerObject()
	{
		board = new PlayerClipboard();
		board.setup();
		board.addBehavior(new ClipboardDrawBehavior());
		board.addBehavior(new ClipboardViewerBehavior());
		
		addChild(board);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (board != null)
		{
			board.update(timestamp, elapsedTime);
		}
	}

	@Override
	public int getUniqueId()
	{
		return GameModels.player.playerId;
	}

	@Override
	public void hold(GameItem item)
	{
		addChild(item);
		GameModels.player.carriedItem = item;
	}

	@Override
	public void removeItem(GameItem item)
	{
		if (GameModels.player.carriedItem == item)
		{
			removeChild(item);
			GameModels.player.carriedItem = null;
		}
	}

	@Override
	public boolean isInteractable()
	{
		return false;
	}

	@Override
	public ArrayList<InteractionAction> getInteractions()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public PhysBox getPhysbox()
	{
		return null;
	}

	@Override
	public Matrix4f getMatrix()
	{
		return null;
	}

	@Override
	public Matrix4f getBoneMatrix(int boneId)
	{
		return null;
	}
	
	@Override
	public GameObject getWorldObject()
	{
		return this;
	}
}

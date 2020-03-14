package zyx.game.components.world.player;

import zyx.game.components.GameObject;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.stencils.StencilControl;
import zyx.opengl.stencils.StencilLayer;

public class PlayerObject extends GameObject
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
	protected void onDraw()
	{
		StencilControl.getInstance().startDrawingToLayer(StencilLayer.PLAYER_CHARACTER, Buffer.DEFERRED);
	}

	@Override
	protected void onPostDraw()
	{
		StencilControl.getInstance().stopDrawingToLayer(StencilLayer.PLAYER_CHARACTER, Buffer.DEFERRED);
	}

}

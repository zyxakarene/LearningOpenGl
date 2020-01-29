package zyx.game.components.world.player;

import zyx.game.components.GameObject;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;
import zyx.opengl.stencils.StencilControl;
import zyx.opengl.stencils.StencilLayer;

public class PlayerObject extends GameObject
{

	@Override
	protected void onDraw()
	{
		StencilControl.drawToLayer(StencilLayer.PLAYER_CHARACTER);
	}

	@Override
	protected void onPostDraw()
	{
		StencilControl.disableStencils();
	}
	
	

}

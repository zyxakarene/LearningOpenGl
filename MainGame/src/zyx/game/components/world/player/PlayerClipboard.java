package zyx.game.components.world.player;

import org.lwjgl.util.vector.Matrix4f;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.utils.interfaces.IPhysbox;

public class PlayerClipboard extends GameObject implements IPhysbox
{

	private SimpleMesh board;
	private SimpleMesh paper;

	public PlayerClipboard()
	{
//		board = new SimpleMesh();
//		paper = new SimpleMesh();
	}

	public void setup()
	{
//		board.load("mesh.player.clipboard");
//		paper.load("mesh.player.paper");
//		paper.setScale(25, 17, 1);
//
//		addChild(board);
//		addChild(paper);
	}

	@Override
	protected void onDispose()
	{
		if (board != null)
		{
			board.dispose();
			board = null;
		}
		
		if (paper != null)
		{
			paper.dispose();
			paper = null;
		}
		
		super.onDispose();
	}

	@Override
	public PhysBox getPhysbox()
	{
		return paper.getPhysbox();
	}

	@Override
	public Matrix4f getMatrix()
	{
		return paper.getMatrix();
	}

	@Override
	public Matrix4f getBoneMatrix(int boneId)
	{
		return paper.getBoneMatrix(boneId);
	}
}

package zyx.game.components.world.player;

import org.lwjgl.util.vector.Matrix4f;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.opengl.materials.StencilLayer;
import zyx.opengl.materials.StencilMode;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.utils.interfaces.IPhysbox;

public class PlayerClipboard extends GameObject implements IPhysbox
{

	private SimpleMesh board;
	private SimpleMesh paper;

	public PlayerClipboard()
	{
		board = new SimpleMesh();
		paper = new SimpleMesh();
	}

	public void setup()
	{
		board.load("mesh.player.clipboard");
		paper.load("mesh.player.paper");
		paper.setScale(25, 17, 1);
		ICallback<SimpleMesh> callback = new ICallback<SimpleMesh>()
		{
			@Override
			public void onCallback(SimpleMesh data)
			{
				WorldModelMaterial material = board.cloneMaterial();
				material.stencilMode = StencilMode.WRITING;
				material.stencilLayer = StencilLayer.PLAYER_CHARACTER;
			}
		};
		
		board.onLoaded(callback);
		
		addChild(board);
		addChild(paper);
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

	@Override
	public GameObject getWorldObject()
	{
		return this;
	}
}

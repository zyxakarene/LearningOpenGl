package zyx.game.components.world.characters;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import zyx.game.behavior.player.OnlinePositionInterpolator;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.GameItem;
import zyx.opengl.models.implementations.physics.PhysBox;

public class GameCharacter extends GameObject implements IItemHolder
{
	private static final ArrayList<InteractionAction> EMPTY_LIST = new ArrayList<>();
	private static final InteractionAction[] EMPTY_ARRAY = new InteractionAction[0];

	private AnimatedMesh mesh;

	public final CharacterInfo info;

	public GameCharacter()
	{
		info = new CharacterInfo();
		mesh = new AnimatedMesh();

		addChild(mesh);
	}

	public void load(CharacterSetupVo vo)
	{
		mesh.load("mesh.player");
		
		setPosition(false, vo.pos);
		lookAt(vo.look);
		
		addBehavior(new OnlinePositionInterpolator());
		
		info.uniqueId = vo.id;
		info.name = vo.name;
	}

	@Override
	public void hold(GameItem item)
	{
		info.heldItem = item;
		mesh.addChildAsAttachment(item, "Character_hand");
	}

	@Override
	public void removeItem(GameItem item)
	{
		if (info.heldItem != null)
		{
			mesh.removeChildAsAttachment(item);
			info.heldItem = null;
		}
	}

	@Override
	public boolean isInteractable()
	{
		return false;
	}

	@Override
	public PhysBox getPhysbox()
	{
		return mesh.getPhysbox();
	}

	@Override
	public Matrix4f getMatrix()
	{
		return mesh.getMatrix();
	}

	@Override
	public Matrix4f getBoneMatrix(int boneId)
	{
		return mesh.getBoneMatrix(boneId);
	}

	@Override
	public ArrayList<InteractionAction> getAvailibleInteractions()
	{
		return EMPTY_LIST;
	}

	@Override
	public InteractionAction[] getAllInteractions()
	{
		return EMPTY_ARRAY;
	}
}

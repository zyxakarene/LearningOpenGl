package zyx.game.components.world.characters;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import zyx.game.behavior.characters.CharacterAnimationBehavior;
import zyx.game.behavior.player.OnlinePositionInterpolator;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.IAnimatedMesh;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.GameItem;
import zyx.game.vo.CharacterType;
import zyx.opengl.models.implementations.physics.PhysBox;

public class GameCharacter extends GameObject implements IItemHolder
{

	private static final ArrayList<InteractionAction> EMPTY_LIST = new ArrayList<>();
	private static final ArrayList<InteractionAction> GUEST_LIST = new ArrayList<>();
	static
	{
		GUEST_LIST.add(InteractionAction.TAKE_ORDER);
	}
	
	private AnimatedMesh mesh;

	public final CharacterInfo info;

	public GameCharacter()
	{
		info = new CharacterInfo();
		mesh = new AnimatedMesh();
		
		addChild(mesh);
	}
	
	public IAnimatedMesh getAnimatedMesh()
	{
		return mesh;
	}

	@Override
	public int getUniqueId()
	{
		return info.uniqueId;
	}
	
	public void load(CharacterSetupVo vo)
	{
		mesh.load("mesh.character");
		setPosition(false, vo.pos);
		lookAt(vo.look);

		addBehavior(new OnlinePositionInterpolator(info));
		addBehavior(new CharacterAnimationBehavior());

		info.uniqueId = vo.id;
		info.name = vo.name;
		info.gender = vo.gender;
		info.type = vo.type;
	}
	
	@Override
	public void hold(GameItem item)
	{
		info.heldItem = item;
		mesh.addChildAsAttachment(item, "bone_carry");
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
		if (info.type == CharacterType.GUEST)
		{
			return true;
		}
		
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
	public GameObject getWorldObject()
	{
		return this;
	}
	
	@Override
	public ArrayList<InteractionAction> getInteractions()
	{
		if (info.type == CharacterType.GUEST)
		{
			return GUEST_LIST;
		}
		else
		{
			return EMPTY_LIST;
		}
	}
}

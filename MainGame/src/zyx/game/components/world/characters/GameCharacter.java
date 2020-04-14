package zyx.game.components.world.characters;

import zyx.game.behavior.player.OnlinePositionInterpolator;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.items.GameItem;
import zyx.utils.interfaces.IPhysbox;

public class GameCharacter extends GameObject implements IItemHolder
{

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
	public IPhysbox getInteractionPhysbox()
	{
		return mesh;
	}

	@Override
	public boolean isInteractable()
	{
		return false;
	}
}

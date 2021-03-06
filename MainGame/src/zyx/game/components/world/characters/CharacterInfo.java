package zyx.game.components.world.characters;

import zyx.game.components.world.furniture.BaseFurnitureItem;
import zyx.game.components.world.items.GameItem;
import zyx.game.vo.CharacterType;
import zyx.game.vo.Gender;

public class CharacterInfo
{
	public int uniqueId;
	public GameItem heldItem;
	public boolean moving;
	public boolean eating;
	public String name;
	public Gender gender;
	public CharacterType type;
	public BaseFurnitureItem interacting;
}

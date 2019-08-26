package zyx.game.components.world.furniture;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.joining.data.FurnitureSetupData;
import zyx.game.vo.FurnitureType;

public class FurnitureSetupVo
{

	int id;
	Vector3f pos;
	Vector3f look;
	FurnitureType type;

	public void fromData(FurnitureSetupData data, int index)
	{
		id = data.ids[index];
		pos = data.positions[index];
		look = data.lookAts[index];
		type = data.types[index];
	}

	public BaseFurnitureItem createFurniture()
	{
		switch (type)
		{
			case FRIDGE:
				return new Fridge();
			case STOVE:
				return new Stove();
		}
		
		return new DummyFurnitureItem();
	}
}

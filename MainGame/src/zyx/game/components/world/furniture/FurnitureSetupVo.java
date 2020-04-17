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
			case CHAIR:
				return new Chair();
			case FOOD_TABLE:
			case DINNER_TABLE:
				return new Table();
			case MONITOR:
				return new Monitor();
			case ORDER_MACHINE:
				return new OrderMachine();
			case FLOOR:
				return new Floor();
			case DISHWASHER:
				return new DishWasher();
		}
		
		return new DummyFurnitureItem();
	}
}

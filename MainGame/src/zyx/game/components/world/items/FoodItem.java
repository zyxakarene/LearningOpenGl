package zyx.game.components.world.items;

import zyx.game.vo.DishType;
import zyx.game.vo.HandheldItemType;
import zyx.opengl.particles.ParticleSystem;

public class FoodItem extends GameItem
{
	private DishType dish;
		
	private ParticleSystem rottenEffect;
	
	public FoodItem(DishType dish)
	{
		super(HandheldItemType.INGREDIENTS);
		this.dish = dish;
	}

	@Override
	public void setType(HandheldItemType type)
	{
		super.setType(type);
		
		clean();
		load();
	}
	
	public void spoil()
	{
		if(rottenEffect == null)
		{
			rottenEffect = new ParticleSystem();
			rottenEffect.load("particles.rotten");
			
			addChild(rottenEffect);
		}
	}
	
	@Override
	protected String getItemResource()
	{
		String foodBase = dish.toString().toLowerCase();
		String foodState;
		
		switch (type)
		{
			case INGREDIENTS:
				foodState = "_raw";
				break;
			case POT:
				foodState = "_cooking";
				break;
			case DIRTY_PLATE:
				foodState = "_dirty";
				break;
			default:
				foodState = "";
				break;
		}
		
		return "mesh.furniture." + foodBase + foodState;
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		if (rottenEffect != null)
		{
			rottenEffect.dispose();
			rottenEffect = null;
		}
	}
}

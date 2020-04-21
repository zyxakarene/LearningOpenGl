package zyx.game.components.world.items;

import java.util.ArrayList;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.vo.DishType;
import zyx.game.vo.FoodState;
import zyx.game.vo.HandheldItemType;
import zyx.opengl.particles.ParticleSystem;

public class FoodItem extends GameItem<FoodState>
{
	private DishType dish;
		
	private ParticleSystem rottenEffect;
	
	public FoodItem(DishType dish)
	{
		super(HandheldItemType.FOOD, FoodState.INGREDIENTS);
		this.dish = dish;
	}

	@Override
	public void setSubType(FoodState subType)
	{
		super.setSubType(subType);
		
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
		
		switch (subType)
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

	@Override
	public ArrayList<InteractionAction> getInteractions()
	{
		ArrayList<InteractionAction> options = new ArrayList<>();
		options.add(InteractionAction.CLOSE);
		
		if (!inUse)
		{
			options.add(InteractionAction.TAKE);
		}
		
		return options;
	}
}

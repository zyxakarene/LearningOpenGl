package zyx.game.components.world.furniture;

import java.util.ArrayList;
import zyx.engine.components.world.World3D;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.GameItem;
import zyx.game.models.GameModels;
import zyx.utils.GeometryUtils;

public class Floor extends BaseFurnitureItem<SimpleMesh>
{

	private static final InteractionAction[] ALL_OPTIONS = new InteractionAction[]
	{
		InteractionAction.CLOSE,
		InteractionAction.PLACE,
		InteractionAction.TAKE
	};

	public Floor()
	{
		super(false);
		setScale(0.01f, 0.01f, 0.01f);
	}

	@Override
	protected String getResource()
	{
		return "mesh.box";
	}

	@Override
	protected void onGotItem(GameItem item)
	{
		World3D.instance.addChild(item);
		item.setDir(true, GeometryUtils.ROTATION_X);
	}

	@Override
	protected void onLostItem(GameItem item)
	{
		World3D.instance.removeChild(item);
	}
	
	@Override
	public boolean isInteractable()
	{
		return false;
	}

	@Override
	public ArrayList<InteractionAction> getAvailibleInteractions()
	{
		ArrayList<InteractionAction> options = new ArrayList<>();
		options.add(InteractionAction.CLOSE);
		
		if (GameModels.player.carriedItem != null)
		{
			options.add(InteractionAction.PLACE);
		}
		
		return options;
	}

	@Override
	public InteractionAction[] getAllInteractions()
	{
		return ALL_OPTIONS;
	}
}

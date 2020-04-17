package zyx.game.components.screen.hud;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import zyx.engine.components.world.WorldObject;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.engine.utils.worldpicker.IWorldPickedItem;
import zyx.game.components.screen.radial.RadialMenu;
import zyx.game.components.world.interactable.IInteractable;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.controls.input.MouseData;
import zyx.utils.GameConstants;

public class DinerHud extends BaseHud implements IWorldPickedItem
{

	private RadialMenu radialMenu;

	public DinerHud()
	{
	}

	@Override
	public String getResource()
	{
		return "json.diner_hud";
	}

	@Override
	protected String[] getDependencies()
	{
		return new String[]
		{
			"json.interaction.radial_menu"
		};
	}

	@Override
	protected void onComponentsCreated()
	{
		super.onComponentsCreated();

		radialMenu = this.<RadialMenu>getComponentByName("radial_menu");
	}

	@Override
	public void onGeometryPicked(ClickedInfo info)
	{
		CursorManager.getInstance().setCursor(GameCursor.HAND);

		if (MouseData.data.isLeftClicked())
		{
			Mouse.setGrabbed(false);
			Mouse.setCursorPosition(ScreenSize.width / 2, ScreenSize.height / 2);

			WorldObject worldObject = info.worldObject;
			if (worldObject instanceof IInteractable)
			{
				IInteractable target = ((IInteractable) worldObject);
				ArrayList<InteractionAction> availibleInteractions = target.getAvailibleInteractions();
				
				radialMenu.showFor(availibleInteractions);
			}
		}
	}

	public void hideRadialMenu()
	{
		radialMenu.visible = false;
	}
}

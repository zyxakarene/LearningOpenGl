package zyx.game.components.screen.radial;

public class RadialMenuCloseOption implements IRadialMenuOption
{
	public static final RadialMenuCloseOption instance = new RadialMenuCloseOption();
	
	private RadialMenuCloseOption()
	{
	}
	
	@Override
	public int getUniqueId()
	{
		return -1;
	}

	@Override
	public String getText()
	{
		return "Close";
	}

	@Override
	public String getIconResource()
	{
		return "icon_close";
	}
}

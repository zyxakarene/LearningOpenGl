package zyx;

import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.game.controls.sound.SoundManager;
import zyx.utils.cheats.DebugPoint;
import zyx.engine.utils.worldpicker.IWorldPickedItem;
import zyx.game.controls.input.MouseData;

public class OnTeaPotClicked implements IWorldPickedItem
{
	@Override
	public void onGeometryPicked(ClickedInfo info)
	{
		CursorManager.getInstance().setCursor(GameCursor.HAND);

		if (MouseData.data.isLeftClicked())
		{
			DebugPoint.addToScene(info.position, 5000);
			SoundManager.getInstance().playSound("sound.Explosion", info.position);
		}
	}
}

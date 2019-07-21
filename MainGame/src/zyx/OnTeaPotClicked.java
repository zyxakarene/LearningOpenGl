package zyx;

import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.engine.utils.worldpicker.IHoveredItem;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.sound.SoundManager;
import zyx.utils.cheats.DebugPoint;

public class OnTeaPotClicked implements IHoveredItem
{

	@Override
	public void onClicked(ClickedInfo data)
	{
		CursorManager.getInstance().setCursor(GameCursor.HAND);

		if (MouseData.data.isLeftDown())
		{
//			data.target.setTexture(new ColorTexture((int) (0xFFFFFF * FloatMath.random())));
		}

		if (MouseData.data.isLeftClicked())
		{
			DebugPoint.addToScene(data.position, 1000);

			SoundManager.getInstance().playSound("sound.Explosion", data.gameObject);
		}
	}
}

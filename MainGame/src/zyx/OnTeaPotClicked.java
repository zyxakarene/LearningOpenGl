package zyx;

import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.callbacks.ICallback;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.game.controls.input.MouseData;
import zyx.opengl.textures.ColorTexture;
import zyx.utils.FloatMath;

class OnTeaPotClicked implements ICallback<ClickedInfo>
{

	public OnTeaPotClicked()
	{
	}

	@Override
	public void onCallback(ClickedInfo data)
	{
		CursorManager.getInstance().setCursor(GameCursor.HAND);
		
		if (MouseData.instance.isLeftDown())
		{
			data.target.setTexture(new ColorTexture((int) (0xFFFFFF * FloatMath.random())));
		}
	}

}

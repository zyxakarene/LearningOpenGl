package zyx.engine.components.screen.base;

import zyx.game.controls.input.MouseData;

public interface ITouched
{
	public void onTouched(boolean collided, MouseData data);
}

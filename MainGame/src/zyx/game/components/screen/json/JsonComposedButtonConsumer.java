package zyx.game.components.screen.json;

import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.composed.ComposedButtonColorMap;
import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.composed.IComposedButton;

public class JsonComposedButtonConsumer<T extends DisplayObjectContainer & IComposedButton> extends JsonComposedConsumer<T>
{
	protected static final String COLOR_SCHEME = "colorScheme";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case COLOR_SCHEME:
				ComposedButtonColorMap colors = ComposedConstants.buttonColorsFromScheme(value.toString());
				currentDisplayObject.setColors(colors.upColors, colors.hoverColors, colors.downColors);
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

}
